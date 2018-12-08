import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Examine{
	public static int rightLS(String [] metro, String eneterdStation) {	
		//입력한 역과 선택한 호선이 매치가 되는지 확인하는 매서드
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
		String metro6[] = new String[39];
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

class UI extends JFrame{
	static int toiletinst3[] = {5, 11, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 27, 28, 31, 32, 35, 37, 40, 41, 42};		//3호선에서 개찰구 내의 화장실이 있는 역의 인덱스 값
	static int toiletinst6[] = {9, 10, 22, 28, 29, 30, 31, 35};															//6호선에서 개찰구 내의 화장실이 있는 역의 인덱스 값

	String enteredStation;		//JTextField에 입력한 내용
	int selectedLine;			//선택한 호선 인덱스 값으로 1이면 3호선, 2이면 6호선을 의미
	int selectedDirection;		//선택한 방향

	String metroLine[] = {"호선","3호선","6호선"};				//호선 종류
	Vector<String> stationSE = new Vector<String>();		//방향 종류

	Container contentPane = getContentPane();
	JComboBox<String> linenum = new JComboBox<String>(metroLine);		//배열 metroLine을 리스트로 하는 JCombobox 생성
	JLabel stname = new JLabel();		//'역' 이라는 문자 출력
	JTextField tf = new JTextField(10); //역 이름을 입력하는 JTextField
	JButton okay= new JButton();		//입력 버튼
	JLabel result = new JLabel();		//결과 출력

	String metro3[] = StoreStation.makeArrayMetro3();
	String metro6[] = StoreStation.makeArrayMetro6();

	public UI() {

		stationSE.add(0, "방향");	
		JComboBox<String> mtdirection = new JComboBox<String>(stationSE);	//벡터 stationSE를 리스트로 하는 JComboBox 생성

		contentPane.setLayout(null);

		contentPane.setBackground(Color.ORANGE);

		linenum.setBounds(50, 50, 70, 30);
		contentPane.add(linenum);

		linenum.addActionListener(new ActionListener() {	//호선을 선택했을 때 벡터에 양 끝의 종착역을 추가
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> sl = (JComboBox<String>)e.getSource();
				int num = sl.getSelectedIndex();
				//Object obj = e.getItem();
				switch (num) {
				case 1: 
					if(stationSE.size()==1) {
						stationSE.add(1, metro3[0] + "행");
						stationSE.add(2, metro3[metro3.length-1] + "행");
					}
					else {
						stationSE.removeAllElements();
						stationSE.add(0, "방향");
						stationSE.add(1, metro3[0] + "행");
						stationSE.add(2, metro3[metro3.length-1] + "행");
					}
					break;
				case 2:
					if(stationSE.size()==1) {
						stationSE.add(1, metro6[0] + "행");
						stationSE.add(2, metro6[metro6.length-1] + "행");
					}
					else {
						stationSE.removeAllElements();
						stationSE.add(0,  "방향");
						stationSE.add(1, metro6[0] + "행");
						stationSE.add(2, metro6[metro6.length-1] + "행");
					}
					break;
				default:
					stationSE.removeAllElements();
					stationSE.add(0,  "방향");
					break;
				}
			}
		}

				);

		stname.setText("역:");
		stname.setBounds(140, 50, 30, 30);
		contentPane.add(stname);

		tf.setBounds(170, 50, 120, 30);
		contentPane.add(tf);

		mtdirection.setBounds(320, 50, 130, 30);
		contentPane.add(mtdirection);

		okay.setText("입력");
		okay.setBounds(190, 100, 70, 30);
		contentPane.add(okay);

		result.setText("정보를 입력해주세요.");
		result.setBounds(175, 170, 170, 30);
		contentPane.add(result);

		tf.addKeyListener(new KeyListener() {	//JTextField에서 엔터기를 누르면 개찰구 내에 화장실이 있는 가장 가까운 역을 찾는 연산 실행

			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==e.VK_ENTER) {
					selectedLine = linenum.getSelectedIndex();
					selectedDirection = mtdirection.getSelectedIndex();
					enteredStation = tf.getText();

					findStation();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});

		okay.addActionListener(new ActionListener() {	//입력 버튼을 누르면 개찰구 내에 화장실이 있는 가장 가까운 역을 찾는 연산 실행
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				if(b.getText().equals("입력")) {
					selectedLine = linenum.getSelectedIndex();
					selectedDirection = mtdirection.getSelectedIndex();
					enteredStation = tf.getText();

					findStation();
				}

			}
		});		

		setTitle("Toilet in Station");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(500, 300);
		setVisible(true);	
	}

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
					result.setBounds(175, 170, 150, 30);
					contentPane.add(result);
				}
				else if(num >= 100 && num < 200) {
					result.setText("집으로 Run...");
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

		else if(selectedLine == 2) {	//6호선 
			int num = Search.searchClosest(enteredStation, selectedDirection, metro6, toiletinst6);
			int cor = Input.correctInput(enteredStation, selectedDirection, metro6, toiletinst6);
			if(cor==1) {
				if(num!=1220 && num<100 && num>=0) {
					if(Examine.rightLS(metro6, enteredStation)==1) {
						result.setText(metro6[num]);
						result.setBounds(215, 170, 150, 30);
						contentPane.add(result);
					}
				}
				else if(num < 0 && num >= -100) {
					result.setText("집으로 Run...");
					result.setBounds(175, 170, 150, 30);
					contentPane.add(result);
				}
				else if(num >= 100 && num < 200) {
					result.setText("집으로 Run...");
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
	}
}

class Input{	
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

public class MetroToiletMain{

	public static void main(String[] args) {

		new UI();
		String metro3[] = StoreStation.makeArrayMetro3();
		String metro6[] = StoreStation.makeArrayMetro6();
	}
}
