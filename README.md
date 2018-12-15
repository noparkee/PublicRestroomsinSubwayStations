# PublicRestroomsinSubwayStations
This project prints out the nearest station where the restroom is located within the turnstile gate, depending on the station and direction you choose.


## Purpose

When you were on the subway,

* when the distance to the destination is quite long
* when you don't know the geography around you

You can't wait to go to the bathroom,

* When you don't want to get off at any station and be in a crowded situation,
* When you don't want to pay for transportation twice,

This program was designed to get to the bathroom as quickly as possible without paying for transportation again in a less crowded environment.


## Code

```java
class Examine{
	public static int rightLS(String [] metro, String eneterdStation) {	
		int cnt = 0;
		for(int i =0 ; i<metro.length; i++) {
			if(metro[i].equals(eneterdStation))
				cnt ++;
		}
		if(cnt !=0)
			return 1; //맞게 입력
		else
			return 0; //잘 못 입력
	}
}
 class Input{	
	//호선과 역이 바르게 매치되는지
	static int correctInput(String station, int selectedDirection, String [] metro, int [] toiletinst) {
		int cnt = 0;
 		for(int i = 0; i<metro.length; i++) {
			if(station.equals(metro[i]))
				cnt++;
		}
		if(cnt == 1)
			return 1;
		else
			return 0;
	}
}
```

>▲Ensure the station matches the line correctly

```java
class StoreStation {	//메모장에 있는 역들을 배열에 저장
	static String[] makeArrayMetro3() {
		String metro3[] = new String[43];
		File fileL3 = new File("metro3.txt");
		try {
			Scanner mt3 = new Scanner(fileL3);
			while(mt3.hasNext()) {
				for(int i =0; i<metro3.length; i++)
					metro3[i] = mt3.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 가져오던 중 오류가 발생했습니다.");
			System.exit(0);
		}
		return metro3;
	}
 	static String[] makeArrayMetro6() {
		String metro6[] = new String[38];
		File fileL6 = new File("metro6.txt");
		try {
			Scanner mt6 = new Scanner(fileL6);
			while(mt6.hasNext()) {
				for(int i =0; i<metro6.length; i++)
					metro6[i] = mt6.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 가져오던 중 오류가 발생했습니다.");
			System.exit(0);
		}
		return metro6;
	}
}
```

>▲Store subway stations stored in txt files in an arraies.

```java
class Search{
	static int searchClosest(String station, int selectedDirection, String [] metro, int [] toiletinst) {
		//개찰구 내에 화장실이 있는 가장 가까운 역을 계산하는 매서드
		//입력한 역의 값을 n이라고 지정하고 n과 인덱스 값의 차이가 가장 작은 역을 출력
		int n=0;
		int min = 100;
		int cnt = 0;
 		if(selectedDirection == 1) {	//상행선(인덱스의 값이 작은 방향)
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= n - toiletinst[j] && n - toiletinst[j] >=0)
							min = n - toiletinst[j];
						else if(toiletinst[j] - n == 0)
							min = 0;
						else if (toiletinst[j] > n) {
							cnt++;
							if(cnt==toiletinst.length) {
								min=100;
							}
						}
					}
				}
			}
			return n-min;
		}
 		else if(selectedDirection == 2) {	//하행선(인텍스의 값이 큰 방향)
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= toiletinst[j] - n && toiletinst[j] - n >0)
							min = toiletinst[j] - n;
						else if(toiletinst[j] - n == 0)
							min = 0;
						else if (toiletinst[j] < n) {
							cnt++;
							if(cnt==toiletinst.length) {
								min=100;
							}
						}
					}
				}
			}
			return n+min;
		}
 		else 
			return 1220;
	}
}
```

>▲Calculate the nearest station where the restroom is located within the turnstile gate.

```java
void findStation() {	
		//개찰구내에 화장실이 있는 가장 가까운 역을 출력하는 매서드
		//있다면 그 역이 출력, 선택한 방향에 개찰구내에 화장실이 없는 경우 "집으로 Run..."이 출력
 		if(selectedLine == 1) {		//3호선
			int num = Search.searchClosest(enteredStation, selectedDirection, metro3, toiletinst3);
			int cor = Input.correctInput(enteredStation, selectedDirection, metro3, toiletinst3);
			if(cor==1) {
				if(num!=1220 && num<100 && num >=0) {
					if(Examine.rightLS(metro3, enteredStation)==1) {
						result.setText(metro3[num]);
						result.setBounds(215, 170, 150, 30);
						contentPane.add(result);
					}
				}
				else if(num < 0 && num >= -100) {
					result.setText("집으로 Run...");
					result.setBounds(195, 170, 150, 30);
					contentPane.add(result);
				}
				else if(num >= 100 && num < 200) {
					result.setText("집으로 Run...");
					result.setBounds(195, 170, 150, 30);
					contentPane.add(result);
				}
			}
			else {
				result.setText("입력을 확인해주세요.");
				result.setBounds(175, 170, 150, 30);
				contentPane.add(result);
			}
		}
 		else if(selectedLine == 2) {	//6호선 
			int num = Search.searchClosest(enteredStation, selectedDirection, metro6, toiletinst6);
			int cor = Input.correctInput(enteredStation, selectedDirection, metro6, toiletinst6);
			if(cor==1) {
				if (enteredStation.equals("역촌") || enteredStation.equals("불광") || enteredStation.equals("독바위") || enteredStation.equals("연신내") || enteredStation.equals("구산")) {
					if(selectedDirection == 2) {
						if(num!=1220 && num<100 && num>=0) {
							if(Examine.rightLS(metro6, enteredStation)==1) {
								result.setText(metro6[num]);
								result.setBounds(215, 170, 150, 30);
								contentPane.add(result);
							}
						}
						else if(num < 0 && num >= -100) {
							result.setText("집으로 Run...");
							result.setBounds(195, 170, 150, 30);
							contentPane.add(result);
						}
						else if(num >= 100 && num < 200) {
							result.setText("집으로 Run...");
							result.setBounds(195, 170, 150, 30);
							contentPane.add(result);
						}
						else {
							result.setText("입력을 확인해주세요.");
							result.setBounds(175, 170, 150, 30);
							contentPane.add(result);
						}
					}
					else {
						result.setText("입력을 확인해주세요.");
						result.setBounds(175, 170, 150, 30);
						contentPane.add(result);
					}
				}
				else if(enteredStation.equals("응암") || enteredStation.equals("새절") || enteredStation.equals("증산")) {
					num = Search.searchClosest(enteredStation,2, metro6, toiletinst6);
					if(num!=1220 && num<100 && num>=0) {
						if(Examine.rightLS(metro6, enteredStation)==1) {
							result.setText(metro6[num]);
							result.setBounds(215, 170, 150, 30);
							contentPane.add(result);
						}
					}
					else if(num < 0 && num >= -100) {
						result.setText("집으로 Run...");
						result.setBounds(195, 170, 150, 30);
						contentPane.add(result);
					}
					else if(num >= 100 && num < 200) {
						result.setText("집으로 Run...");
						result.setBounds(195, 170, 150, 30);
						contentPane.add(result);
					}
					else {
						result.setText("입력을 확인해주세요.");
						result.setBounds(175, 170, 150, 30);
						contentPane.add(result);
					}
				}
				else {
					if(num!=1220 && num<100 && num>=0) {
						if(Examine.rightLS(metro6, enteredStation)==1) {
							result.setText(metro6[num]);
							result.setBounds(215, 170, 150, 30);
							contentPane.add(result);
						}
					}
					else if(num < 0 && num >= -100) {
						result.setText("집으로 Run...");
						result.setBounds(195, 170, 150, 30);
						contentPane.add(result);
					}
					else if(num >= 100 && num < 200) {
						result.setText("집으로 Run...");
						result.setBounds(195, 170, 150, 30);
						contentPane.add(result);
					}
					else {
						result.setText("입력을 확인해주세요.");
						result.setBounds(175, 170, 150, 30);
						contentPane.add(result);
					}
				}
			}
			else {
				result.setText("입력을 확인해주세요.");
				result.setBounds(175, 170, 150, 30);
				contentPane.add(result);
			}
		}
	}
```
  
>▲Prints out the nearest station where the restroom is located within the turnstile gate.

## How to use

1. Select the line of the subway you are currently on.
2. Selec the direction you want.
3. Enter the name of the station on which you are riding to match the selected line.
4. Press the Enter button or "입력" button.
* The order of 2 and 3 is irrelevant.

## Execution screen
![1](https://user-images.githubusercontent.com/44026481/50042949-15692580-00af-11e9-883e-ede70776c5c3.PNG)
>Initial Screen

![3](https://user-images.githubusercontent.com/44026481/50042980-cec7fb00-00af-11e9-8d2d-1fef06433697.PNG)
>Wrong input:
>This program prints out "입력을 확인해주세요."

![4](https://user-images.githubusercontent.com/44026481/50042988-ebfcc980-00af-11e9-9dd3-4d8a00bb60fb.PNG)
>Right input:
>This program prints out the nearest station where the restroom is located within the turnstile gate.

## Execution video
https://youtu.be/UeQ4PdC83Ks

## Limit
This project only considered lines 3 and 6.
This project did not consider transfer to another line.
