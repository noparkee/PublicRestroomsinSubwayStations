import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class StoreStation {
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
	
	public UI() {
		
		String metroLine[] = {"3호선","6호선"};
		String metro3[] = StoreStation.makeArrayMetro3();
		String metro6[] = StoreStation.makeArrayMetro6();
		
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
				
		JComboBox<String> linenum = new JComboBox<String>(metroLine); //호선 선택
		linenum.setBounds(50, 50, 70, 30);
		linenum.setOpaque(true);
		contentPane.add(linenum);
	
		JLabel stname = new JLabel();
		stname.setText("역:");
		stname.setBounds(150, 50, 30, 30);
		contentPane.add(stname);
		
		JTextField tf = new JTextField(10); //역 이름 입력
		tf.setBounds(180, 50, 250, 30);
		contentPane.add(tf);
		String station = tf.getText();
		
		//panel.add(tf);
		
		setTitle("Toilet in Station");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(500, 300);
		
		setVisible(true);
		
	}
}



class Search{
	
	static void searchClosest(int metroline, String station, String direction, String [] metro, int [] toiletinst) {
		int n;
		int min = 100;

		if(direction.equals("상행선")) {
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= n - toiletinst[j] && n - toiletinst[j] >=0)
							min = n - toiletinst[j];
						else if(toiletinst[j] - n == 0)
							min = 0;
					}
					System.out.println(metro[n-min]);
				}

			}
		}
		else if(direction.equals("하행선")) {
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= toiletinst[j] - n && toiletinst[j] - n >0)
							min = toiletinst[j] - n;
						else if(toiletinst[j] - n == 0)
							min = 0;
					}
					System.out.println(metro[n+min]);
				}

			}

		}
	}
}


public class MetroToiletMain{
	
	static int toiletinst3[] = {5, 11, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 27, 28, 31, 32, 35, 37, 40, 41, 42}; 
	static int toiletinst6[] = {9, 10, 22, 28, 29, 30, 31, 35};
	
	public static void main(String[] args) {
		
		new UI();
		String metro3[] = StoreStation.makeArrayMetro3();
		String metro6[] = StoreStation.makeArrayMetro6();
		
		/*static String metro3[] = new String[43];
		static String metro6[] = new String[39];

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
		}*/
		//-----------------↑3호선, 6호선 배열 정리↑---------------------------------
		
		/*Scanner a = new Scanner(System.in);
		System.out.print("현재 탑승한 지하철 호선: ");
		int metroline = a.nextInt();
		System.out.print("현재 위치한 역: ");
		String station = a.next();
		System.out.print("상행선(내선) or 하행선(외선): ");
		String direction = a.next();
		*/
		
		/*if(metroline == 3)
			Search.searchClosest(metroline, station, direction, metro3, toiletinst3);*/

		

	}

}
