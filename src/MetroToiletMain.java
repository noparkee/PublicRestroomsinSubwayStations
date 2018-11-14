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
	static int toiletinst3[] = {5, 11, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 27, 28, 31, 32, 35, 37, 40, 41, 42}; 
	static int toiletinst6[] = {9, 10, 22, 28, 29, 30, 31, 35};
	
	String enteredStation;
	int selectedLine;
	int selectedDirection;
	
	public UI() {
		
		String metroLine[] = {"호선","3호선","6호선"};
		String metroDirection[] = {"방향","상행선", "하행선", "내선순환", "외선순환"};
		String metro3[] = StoreStation.makeArrayMetro3();
		String metro6[] = StoreStation.makeArrayMetro6();

		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		contentPane.setBackground(Color.ORANGE);

		JComboBox<String> linenum = new JComboBox<String>(metroLine); //호선 선택
		linenum.setBounds(50, 50, 70, 30);
		contentPane.add(linenum);

		JLabel stname = new JLabel();
		stname.setText("역:");
		stname.setBounds(170, 50, 30, 30);
		contentPane.add(stname);

		JTextField tf = new JTextField(10); //역 이름 입력
		tf.setBounds(200, 50, 120, 30);
		contentPane.add(tf);

		JComboBox<String> mtdirection = new JComboBox<String>(metroDirection);
		mtdirection.setBounds(370, 50, 70, 30);
		contentPane.add(mtdirection);

		JButton okay= new JButton();
		okay.setText("입력");
		okay.setBounds(210, 100, 70, 30);
		contentPane.add(okay);

		JLabel result = new JLabel();
		
		/*tf.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					enteredStation = tf.getText();
				}
			}	//역 이름 입력하고 엔터하면 enteredStation에 역 이름 저장
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});

		mtdirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedDirection = mtdirection.getSelectedIndex();
			}
		});
		
		linenum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedLine = linenum.getSelectedIndex();
			}
		});*/

		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				if(b.getText().equals("입력")) {
					
					selectedDirection = linenum.getSelectedIndex();
					
					//JComboBox sll = (JComboBox)e.getSource();
					selectedLine = mtdirection.getSelectedIndex();
					
					enteredStation = tf.getText();
					
					
					if(selectedLine == 1) {
						int num = Search.searchClosest(enteredStation, selectedDirection, metro3, toiletinst3);
						result.setText(metro3[num]);
						result.setBounds(200, 180, 100, 30);
						contentPane.add(result);
						System.out.println(result);
					}
					
					else if(selectedLine == 2) {
						int num2 = Search.searchClosest(enteredStation, selectedDirection, metro6, toiletinst6);
						result.setText(metro6[num2]);
						result.setBounds(200, 170, 100, 30);
						contentPane.add(result);
						System.out.println(result);
					}
					
				}

			}
	});		

		/*if(selectedLine == 1) {
			JLabel result = new JLabel();
			result.setText(Search.searchClosest(enteredStation, selectedDirection, metro3, toiletinst3));
			result.setBounds(100, 100, 70, 30);
			contentPane.add(result);
		}*/

		setTitle("Toilet in Station");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(500, 300);
		setVisible(true);	

}
}

class Search{
	//n을 for문 밖으로 꺼냈을 떄도 i와 값이 같아야하는데 이를 고쳐야하뮤ㅠ
	
	static int searchClosest(String station, int selectedDirection, String [] metro, int [] toiletinst) {
		int n=0;
		int min = 100;
		
		if(selectedDirection == 1 || selectedDirection == 3) {
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= n - toiletinst[j] && n - toiletinst[j] >=0)
							min = n - toiletinst[j];
						else if(toiletinst[j] - n == 0)
							min = 0;
					}
					//System.out.println(metro[n-min]);
				}

			}
			return n-min;
		}

		else if(selectedDirection == 2 || selectedDirection == 4) {
			for(int i =0; i<metro.length; i++) {
				if(metro[i].equals(station)) {
					n = i;
					for(int j = 0; j<toiletinst.length; j++) {
						if(min >= toiletinst[j] - n && toiletinst[j] - n >0)
							min = toiletinst[j] - n;
						else if(toiletinst[j] - n == 0)
							min = 0;
					}
					//return metro[n+min];
				//	System.out.println(metro[n+min]);
				}

			}
		return n+min;
		}
		else 
		return 0;
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
