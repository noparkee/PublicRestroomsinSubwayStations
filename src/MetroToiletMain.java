import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Examine{
	public static int rightLS(String [] metro, String eneterdStation) {
		int cnt = 0;
		for(int i =0 ; i<metro.length; i++) {
			// 호선과 역이 맞게 입력되었는지.
			if(metro[i].equals(eneterdStation))
				cnt ++;
		}
		if(cnt !=0)
			return 1; //맞게 입력
		else
			return 0; //잘 못 입력
	}
}

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
	static int toiletinst3[] = {5, 11, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 27, 28, 31, 32, 35, 37, 40, 41, 42}; 
	static int toiletinst6[] = {9, 10, 22, 28, 29, 30, 31, 35};

	String enteredStation;
	int selectedLine;
	int selectedDirection;

	public UI() {

		String metroLine[] = {"호선","3호선","6호선"};
		Vector<String> stationSE = new Vector<String>();
		stationSE.add("방향");

		//String metroDirection[] = {"방향","상행선 (내선순환)", "하행선 (외선순환)"};
		String metro3[] = StoreStation.makeArrayMetro3();
		String metro6[] = StoreStation.makeArrayMetro6();

		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		contentPane.setBackground(Color.ORANGE);

		JComboBox<String> linenum = new JComboBox<String>(metroLine); //호선 선택
		linenum.setBounds(50, 50, 70, 30);
		contentPane.add(linenum);

		//linenum.addItemListener(new MyItemListener());
		linenum.addActionListener(new ActionListener() {	//처음에 선택할때는 옳게 뜨는데 두 번 이상 호선을 바꾸면 선택하면 다르게 뜸. ***여기 수정
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> sl = (JComboBox<String>)e.getSource();
				int num = sl.getSelectedIndex();
				//Object obj = e.getItem();
				switch (num) {
				case 1: 
					if(stationSE.get(1).isEmpty()) {
						stationSE.removeAllElements();
						stationSE.add("방향");
					}
					
					stationSE.add(1, metro3[0] + "행");
					stationSE.add(2, metro3[metro3.length-1] + "행");
					// System.out.println(stationSE.get(2));
					break;
				case 2:
					stationSE.add(1, metro6[0] + "행");
					stationSE.add(2, metro6[metro6.length-1] + "행");
					break;
				default:
					break;
				}
			}
		}

				);

		JLabel stname = new JLabel();
		stname.setText("역:");
		stname.setBounds(140, 50, 30, 30);
		contentPane.add(stname);

		JTextField tf = new JTextField(10); //역 이름 입력
		tf.setBounds(170, 50, 120, 30);
		contentPane.add(tf);

		JComboBox<String> mtdirection = new JComboBox<String>(stationSE);
		//JComboBox<String> mtdirection = new JComboBox<String>(metroDirection);
		mtdirection.setBounds(320, 50, 130, 30);
		contentPane.add(mtdirection);

		JButton okay= new JButton();
		okay.setText("입력");
		okay.setBounds(190, 100, 70, 30);
		contentPane.add(okay);

		JLabel result = new JLabel();
		result.setText("정보를 입력해주세요.");
		result.setBounds(175, 170, 150, 30);
		contentPane.add(result);

		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				if(b.getText().equals("입력")) {

					selectedLine = linenum.getSelectedIndex();
					selectedDirection = mtdirection.getSelectedIndex();
					enteredStation = tf.getText();

					if(selectedLine == 1) {
						int num = Search.searchClosest(enteredStation, selectedDirection, metro3, toiletinst3);
						if(num!=1220) {
							if(Examine.rightLS(metro3, enteredStation)==1) {
								result.setText(metro3[num]);
								result.setBounds(215, 170, 70, 30);
								contentPane.add(result);
								//System.out.println(result);
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
					else if(selectedLine == 2) {

						int num = Search.searchClosest(enteredStation, selectedDirection, metro6, toiletinst6);
						if(num!=1220) {
							if(Examine.rightLS(metro6, enteredStation)==1) {
								result.setText(metro6[num]);
								result.setBounds(200, 170, 100, 30);
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
					else
						result.setText("입력을 확인해주세요.");
					contentPane.add(result);
				}

			}
		});		

		setTitle("Toilet in Station");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(500, 300);
		setVisible(true);	

	}
}

class Search{
	static int searchClosest(String station, int selectedDirection, String [] metro, int [] toiletinst) {
		int n=0;
		int min = 100;

		if(selectedDirection == 1) {
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= n - toiletinst[j] && n - toiletinst[j] >=0)
							min = n - toiletinst[j];
						else if(toiletinst[j] - n == 0)
							min = 0;
					}
				}

			}
			return n-min;
		}

		else if(selectedDirection == 2) {
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= toiletinst[j] - n && toiletinst[j] - n >0)
							min = toiletinst[j] - n;
						else if(toiletinst[j] - n == 0)
							min = 0;
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

	static int toiletinst3[] = {5, 11, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 27, 28, 31, 32, 35, 37, 40, 41, 42}; 
	static int toiletinst6[] = {9, 10, 22, 28, 29, 30, 31, 35};

	public static void main(String[] args) {

		new UI();
		String metro3[] = StoreStation.makeArrayMetro3();
		String metro6[] = StoreStation.makeArrayMetro6();
	}
}
