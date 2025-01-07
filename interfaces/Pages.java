package interfaces;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import fuctions.*;

//유저 정보 가져와서 보여주기
public class Pages {

	private JFrame frame;
	private User user;
	private JButton[] menuList;
	private JTextField[] fields;
	private JLabel[] labels;
	private Vector<Note> notes;
	
	public Pages(User user) {
		this.frame = new JFrame("Diary");
		this.user = user;
		this.menuList = new JButton[5];
		this.notes = user.returnNotes();

		buildGUI();

		frame.setVisible(true);
		
		today();
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
					break;
				}
			}
		});

		createMenubar();
		frame.add(writingSection());
	}

	private void goToMain() {
		frame.dispose();
		new Cover();
	}

	// 메뉴바 만들고 이벤트 처리
	private void createMenubar() {
		JMenuBar mb = new JMenuBar();
		
		ActionListener eventTaker = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String where = e.getActionCommand();
				
				switch(where) {
				case "Logout":
					int n = JOptionPane.showConfirmDialog(frame, "Do you want to Logout?", "System",JOptionPane.YES_NO_OPTION);
					switch (n) {
					case JOptionPane.YES_OPTION:
						goToMain();
						break;

					case JOptionPane.NO_OPTION:
						JOptionPane.showMessageDialog(frame, "Cancelled", "System", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
					break;
					
				case "Previous":
					
					break;

				case "Next":
					
					break;

				case "Calender":
					
					break;

				case "Stats":

					break;
				
				}
				
			}
			
		};

		this.menuList[0] = createMenu("Logout");
		this.menuList[1] = createMenu("Previous");
		this.menuList[2] = createMenu("Next");
		this.menuList[3] = createMenu("Calender");	
		this.menuList[4] = createMenu("Stats");

		for(JButton i : menuList) {
			mb.add(i);
			i.addActionListener(eventTaker);
		}

		frame.setJMenuBar(mb);
	}

	//오늘 일기 여부 확인 함수
	private void today() {
		if(user.checkToday()) {
			JOptionPane.showMessageDialog(frame, "You already wrote diary today.","System",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			int option = JOptionPane.showConfirmDialog(frame, "You haven't written diary today\nDo you want to write it now?","System",JOptionPane.YES_NO_OPTION);
			switch(option) {
			case JOptionPane.YES_OPTION:
				//일기 작성 로직 추가
				break;

			case JOptionPane.NO_OPTION:

				break;
			
				
			}
		}
	}
	
	//기본 writing section 만들고 이후 버튼 누르면 알아서 벡터에서 불러와서 덮어지게 작성 하면 될 듯
	//대신 다시 저장 누르면 알아서 용이하게 바꿔 질 수 있도록
	private JPanel writingSection() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel btnPanel = new JPanel();
		JPanel lbPanel = new JPanel(new GridLayout(3,-1));
		
		JPanel subP1 = new JPanel(new GridLayout(1,2));
		JPanel subP2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subP3 = new JPanel(new BorderLayout());
	
		panel.add(lbPanel,BorderLayout.CENTER);
		panel.add(btnPanel,BorderLayout.SOUTH);
		
		//레이블 날짜, 글쓴이, 제목, 내용
		this.labels = new JLabel[4];
		labels[0] = new JLabel("Date");
		labels[1] = new JLabel("Writer");
		labels[2] = new JLabel("Title");
		labels[3] = new JLabel("Text");
		
		this.fields = new JTextField[4];
		fields[0] = new JTextField(10);
		fields[1] = new JTextField(10);
		fields[2] = new JTextField(10);
		fields[3] = new JTextField(2000);
		
		subP1.add(labels[0]);
		subP1.add(fields[0]);
		
		subP1.add(labels[1]);
		subP1.add(fields[1]);
		
		subP2.add(labels[2]);
		subP2.add(fields[2]);
		
		subP3.add(labels[3]);
		subP3.add(fields[3]);
		
		lbPanel.add(subP1);
		lbPanel.add(subP2);
		lbPanel.add(subP3);
		
		
		//button section
		//save or delete
		JButton save = new JButton("Save");
		JButton delete = new JButton("Delete");
		
		btnPanel.add(save);
		btnPanel.add(delete);
		
		ActionListener eventTaker = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String option = e.getActionCommand();
				
				switch(option) {
				
				case "Save":
					if(isFilled()) {
						JOptionPane.showMessageDialog(frame, "Saved!","System",JOptionPane.INFORMATION_MESSAGE);
						
					}
					else {
						JOptionPane.showMessageDialog(frame, "Please fill the blanks","System",JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				case "Delete":
					break;
				}
				
			}
			
		};
		
		save.addActionListener(eventTaker);
		delete.addActionListener(eventTaker);
		
		return panel;
		
	}
	// 레이블이 다 작성되었는지 확인하는 함수
	private boolean isFilled() {
		for(JTextField i : this.fields) {
			if(i.getText().trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	// 메인가기 메뉴 | 이전 일기 | 다음 일기 | 달력메뉴 | 통계
	// 처음 화면은 안 작성 했다면, 작성 바로 창 나오게, 작성 했다면 읽을 수 있게
	// 메뉴
	private JButton createMenu(String option) {
		JButton menu = null;
		switch (option) {
		case "Logout":
			menu = new JButton("Logout");
			break;
			
		case "Previous":
			menu = new JButton("Previous");
			break;

		case "Next":
			menu = new JButton("Next");
			break;

		case "Calender":
			menu = new JButton("Calender");
			break;

		case "Stats":
			menu = new JButton("Stats");
			break;
		}
		
		return menu;
	}
}
