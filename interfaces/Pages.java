package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import fuctions.*;

//유저 정보 가져와서 보여주기
public class Pages {

	private JFrame frame;
	private User user;
	private JMenu[] menuList;

	public Pages(User user) {
		this.frame = new JFrame("Diary");
		this.user = user;
		this.menuList = new JMenu[5];

		buildGUI();

		frame.setVisible(true);
	}

	private void buildGUI() {
		frame.setBounds(100, 100, 500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int n = JOptionPane.showConfirmDialog(frame, "Do you want to go main?", "System",
						JOptionPane.YES_NO_OPTION);

				switch (n) {
				case JOptionPane.YES_OPTION:
					goToMain();
					break;

				case JOptionPane.NO_OPTION:
					JOptionPane.showMessageDialog(frame, "Cancelled", "System", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
		});

		createMenubar();
	}

	private void goToMain() {
		frame.dispose();
		new Cover();
	}

	// 메뉴바 만들고 메뉴 붙이기
	private void createMenubar() {
		JMenuBar mb = new JMenuBar();
		ActionListener eventTaker = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		};

		this.menuList[0] = createMenu("Main");
		this.menuList[1] = createMenu("Previous");
		this.menuList[2] = createMenu("Next");
		this.menuList[3] = createMenu("Calender");	
		this.menuList[4] = createMenu("Stats");

		for(JMenu i : menuList) {
			mb.add(i);
			i.addActionListener(eventTaker);
		}

		frame.setJMenuBar(mb);
	}

	// 메인가기 메뉴 | 이전 일기 | 다음 일기 | 달력메뉴 | 통계
	// 처음 화면은 안 작성 했다면, 작성 바로 창 나오게, 작성 했다면 읽을 수 있게
	// 메뉴
	private JMenu createMenu(String option) {
		JMenu menu = null;
		switch (option) {
		case "Main":
			menu = new JMenu("Login");
			break;
			
		case "Previous":
			menu = new JMenu("Previous");
			break;

		case "Next":
			menu = new JMenu("Next");
			break;

		case "Calender":
			menu = new JMenu("Calender");
			break;

		case "Stats":
			menu = new JMenu("Stats");
			break;
		}
		
		return menu;
	}
}
