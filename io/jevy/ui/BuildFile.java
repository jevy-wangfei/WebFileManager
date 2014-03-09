package io.jevy.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class BuildFile extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	File file = null;
	JButton b1 = new JButton("Choose a Directory…");
	JButton b2 = new JButton(" OK ");
	JButton b3 = new JButton("Reset the Operation");
	JScrollPane jp = new JScrollPane();
	JTextArea area = new JTextArea("", 20, 55);
	JTextField tf1;
	JFileChooser fc;
	PrintStream ps;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
//				JFrame.setDefaultLookAndFeelDecorated(true);
				try {
					UIManager
							.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					// UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					// 中文乱码问题解决
					UIManager.getLookAndFeelDefaults().put("defaultFont",
							new Font("Microsoft Yahei", Font.PLAIN, 12));
				} catch (Exception e) {
					e.printStackTrace();
				}
				BuildFile b = new BuildFile();
				b.setTitle("Buide Files");
				b.setBounds(100, 100, 650, 500);
				b.setLayout(new FlowLayout());
				b.setResizable(false);
				b.tf1 = new JTextField(45);
				b.tf1.setText("D:\\webpages");
				b.area.setEditable(false);
				b.area.setLineWrap(true);

				b.jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				b.jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				b.jp.getViewport().add(b.area, null);

				b.b1.addActionListener(b);
				b.b2.addActionListener(b);
				b.b3.addActionListener(b);
				b.add(b.tf1);
				b.add(b.b1);
				b.add(b.b2);
				b.add(b.b3);
				b.add(b.jp);
				//b.pack();
				b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				b.setVisible(true);
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			fc = new JFileChooser();

			int result = fc.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String file = fc.getSelectedFile().getParent();
				String str = file.toString();
				System.out.println(str);
				tf1.setText(str);
				fc.update(this.getGraphics());
			}
		} else if (e.getSource() == b2) {
			if (null != b2) {
				String str = tf1.getText();
				// str = str.replaceAll("\\\\", "\\");
				file = new File(str);
				new ThreadBuild().start();
			}
		} else if (e.getSource() == b3) {
			if (null != b3) {
				String str = tf1.getText();
				// str = str.replaceAll("\\\\", "\\");
				System.out.println(str);
				file = new File(str);
				new ThreadDelete().start();
			}
		}
	}

	class ThreadBuild extends Thread {
		public void run() {
			new Build().build(file);
		}
	}

	class ThreadDelete extends Thread {
		public void run() {
			new Delete().delete(file);
		}
	}

	class Build {
		public void build(File file) {
			if (!file.toString().contains("files")) {
				File[] fileList = file.listFiles();
				// System.out.println(file.toString().codePointCount(file.toString().length()-6,
				// file.toString().length()-1));
				String path = file.getPath();
				try {
					String[] strs = path.split("\\\\");
					ps = new PrintStream(path + "\\" + strs[strs.length - 1]
							+ ".htm");
					ps.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><HTML xmlns=\"http://www.w3.org/1999/xhtml\"><HEAD><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><html> <head><title>"
									+ strs[strs.length - 1]
									+ "</title></head> <body>");
				} catch (Exception e) {

				}
				for (int i = 0; i < fileList.length; i++) {
					if (!fileList[i].toString().contains("files")) {
						if (fileList[i].isDirectory()) {
							String[] strs = fileList[i].toString()
									.split("\\\\");
							ps.append("<a href=\"file:///" + fileList[i] + "\\"
									+ strs[strs.length - 1] + ".htm\">"
									+ fileList[i] + "</a><br>");
							String str1 = "Building Directory: "
									+ fileList[i].getPath()
									+  "\n";
							area.append(str1);
							area.setCaretPosition(area.getText().length());
						}
						if (fileList[i].toString().contains("htm")
								|| fileList[i].toString().contains("html")) {
							ps.append("<a href=\"file:///" + fileList[i]
									+ "\">" + fileList[i] + "</a><br>");
							String str1 = "Building Directory: "
									+ fileList[i].getPath() + "\n";
							area.append(str1);
							area.setCaretPosition(area.getText().length());
						}
					}
				}

				ps.append("</body></html>");
				for (int j = 0; j < fileList.length; j++) {
					if (fileList[j].isDirectory()) {
						build(fileList[j]);
					}
				}
			}
		}
	}

	class Delete {
		public void delete(File file) {
			if (!file.toString().contains("files")) {
				File[] fileList = file.listFiles();
				String path = file.getPath();
				try {
					String[] strs = path.split("\\\\");
					String str = strs[strs.length - 1];

					str = str + ".htm";
					for (int i = 0; i < fileList.length; i++) {
						if (str.equals(fileList[i].getName().toString())) {
							area.append("Remove file: " + path + "\\" + str
									+ "\n");
							area.setCaretPosition(area.getText().length());
							fileList[i].delete();
						}
					}
				} catch (Exception e) {

				}

				for (int j = 0; j < fileList.length; j++) {
					if (fileList[j].isDirectory()) {
						delete(fileList[j]);
					}
				}
			}
		}
	}
}
