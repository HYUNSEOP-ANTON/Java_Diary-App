package interfaces;

import javax.swing.*;
import fuctions.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

//커버에 구현할 것은 
//로그인 창 + 회원 등록 창 +종료 버튼 연계
public class Cover {

	private JFrame frame;
	private Vector<User> users;

	public Cover() {
		frame = new JFrame("SYSTEM");
		users = new Vector<User>();
		//처음에 불러오기
		loadingInfos();
		
		BuildGUI();
		
		frame.setVisible(true);
	}

	private void BuildGUI() {
		frame.setBounds(100, 100, 500, 500);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(2, 1));
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// System.out.println("x 눌림");

				int n = JOptionPane.showConfirmDialog(frame, "Do you want to close the app?", "Exit?",
						JOptionPane.YES_NO_OPTION);

				switch (n) {
				case JOptionPane.YES_OPTION:
					writingInfos();
					System.exit(-1);
				case JOptionPane.NO_OPTION:
					JOptionPane.showMessageDialog(frame, "Cancelled", "System", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JPanel lower = lowerDeck();
		JPanel upper = upperDeck();

		frame.add(upper);
		frame.add(lower);

	}

	private JPanel upperDeck() {
		JPanel upper = new JPanel();

		upper.setLayout(new BorderLayout());

		JLabel title = new JLabel("MAIN", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));

		upper.add(title, BorderLayout.CENTER);

		return upper;
	}

	private JPanel lowerDeck() {
		JPanel lower = new JPanel();
		lower.setLayout(null);

		JButton btn1 = new JButton("Sign Up");
		JButton btn2 = new JButton("Login");

		JTextField text1 = new JTextField();
		JTextField text2 = new JTextField();

		JLabel label1 = new JLabel("User ID");
		JLabel label2 = new JLabel("Password");

		btn1.setBounds(150, 50, 85, 30);
		btn2.setBounds(250, 50, 70, 30);

		label1.setBounds(100, 100, 70, 30);
		label2.setBounds(100, 130, 70, 30);

		text1.setBounds(165, 105, 150, 25);
		text2.setBounds(165, 135, 150, 25);

		lower.add(btn1);
		lower.add(btn2);

		lower.add(label1);
		lower.add(label2);

		lower.add(text1);
		lower.add(text2);

		// 이벤트 연결부
		// Register 부분
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registerFrame(users);
				writingInfos();
			}
		});

		// Login 부분
		btn2.addActionListener(new ActionListener() {

			private String id, pwd;
			private int index;

			@Override
			public void actionPerformed(ActionEvent e) {

				id = text1.getText();
				pwd = text2.getText();

				this.index = loginThrough(id, pwd);

				if (index >= 0) {
					loginInto(index);
					writingInfos();
				}
			}
		});
		return lower;
	}

	// 등록 창
	private void registerFrame(Vector<User> type) {
		JDialog register = new JDialog(this.frame, "Register New Account", true);

		register.setLayout(null);
		register.setBounds(200, 200, 300, 300);
		register.setResizable(false);

		JTextField[] texts = new JTextField[3];
		JLabel[] labels = new JLabel[3];
		JButton btn1 = new JButton("Confirm");
		JLabel title = new JLabel("Register");

		title.setBounds(90, 10, 200, 100);
		title.setFont(new Font("Arial", Font.BOLD, 25));
		btn1.setBounds(100, 220, 100, 30);

		labels[0] = new JLabel("Name");
		labels[1] = new JLabel("User ID");
		labels[2] = new JLabel("Password");

		for (int i = 0; i < texts.length; i++) {
			texts[i] = new JTextField(20);
			labels[i].setBounds(40, 100 + 40 * i, 100, 30);
			texts[i].setBounds(120, 100 + 40 * i, 100, 30);
			register.add(texts[i]);
			register.add(labels[i]);
		}

		btn1.addActionListener(new ActionListener() {

			private boolean flag;
			String[] infos = new String[3];

			@Override
			public void actionPerformed(ActionEvent e) {

				this.flag = true;
				for (JTextField i : texts) {
					if (i.getText().trim().isEmpty()) {
						flag = false;
						break;
					}
				}
				if (flag) {
					for (int i = 0; i < infos.length; i++) {
						infos[i] = texts[i].getText();
						// System.out.println(infos[i]);
					}

					users.add(new User(infos[0], infos[1], infos[2]));
					// users.get(0).showUserLog();
					writingInfos();
					JOptionPane.showMessageDialog(register, "Signed up!", "Done", JOptionPane.INFORMATION_MESSAGE);
					register.dispose();
				} else {
					JOptionPane.showMessageDialog(register, "Please fill the all informations", "Error!",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		});

		register.add(btn1);
		register.add(title);
		register.setVisible(true);
	}

	// 로그인 부분 만들기
	private int loginThrough(String ID, String PWD) {

		boolean right = false;
		int index = -1;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).returnID().equals(ID)) {
				if (users.get(i).returnPWD().equals(PWD)) {
					index = i;
					right = true;
					break;
				}
			}
		}
		if (right) {
			System.out.println("ok");
			return index;
		} else {
			System.out.println("failed");
			JOptionPane.showMessageDialog(frame, "Check your ID or Password again.", "Error!",
					JOptionPane.ERROR_MESSAGE);
			return index;
		}
	}

	// 로그인 후 화면 만들기
	private void loginInto(int index) {
		
		frame.dispose();
		User thisUser = users.get(index);
		new Pages(thisUser);
		
	}
	
	// 유저 정보 입출력
	// 저장하기
	private void writingInfos() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream("data.dat");
			oos = new ObjectOutputStream(fos);
			
			for(int i = 0 ; i < users.size();i++) {
				oos.writeObject(users.get(i));
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				if(oos !=null) oos.close();
				if(fos !=null) fos.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	// 불러오기
	private void loadingInfos() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream("data.dat");
			ois = new ObjectInputStream(fis);

			while(true) {
				try {
					Object temp = ois.readObject();
					users.add((User)temp);
				}
				catch (IOException e) {
					break;
				}
			}
			
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(ois != null) ois.close();
				if(fis != null) fis.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	
	}
}
