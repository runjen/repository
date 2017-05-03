package cn.runjen.common.utils;

import java.io.*;


public class FileUtils {

	public static String readToString(String fileName) {
		String encoding = "ISO-8859-1";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("编码有问题" + encoding);
			e.printStackTrace();
			return null;
		}
	}

	public static String readFirstLine(String fileName) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		BufferedReader bf = new BufferedReader(fileReader);
		try {
			String line = bf.readLine();
			bf.close();
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					fileReader = null;
				}
			}
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					fileReader = null;
				}
			}

			return null;
		}
	}

	public static boolean writeFile(String name, String splitTxt) {
		try {
			File f = new File(name);
			if (f.exists()) {
				System.out.println("文件存在");
				return false;
			} else {
				System.out.println("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(splitTxt);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return true;
	}

	public static void appendString(String path, String content) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, true)));
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
