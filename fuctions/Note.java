package fuctions;

import java.io.*;
import java.time.*;
import javax.swing.*;

public class Note implements Serializable{
	// 기본적으로 현재 시간 저장용
	private LocalDate now;
	//글이 실질 적으로 쓰여지는 곳
	private StringBuffer sentences;
	
	private JTextArea area;
	private JLabel timeLine;
	
	
	public Note() {
		this.now = LocalDate.now();
		this.sentences = new StringBuffer();
		this.area = new JTextArea();
		this.timeLine = new JLabel();
	}
	
	
}
