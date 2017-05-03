package cn.runjen.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SplitFile {
	private int lineSize;
	private long lines;
	private int code;
	private int start;



	public SplitFile(int code,int start,int end) {
		super();
		this.code = code;
		this.start = start;
	}

	public void splitFile(String srcFile, String desFile, int count) {
		String SEPARATOR = System.getProperty("line.separator");// 系统换行符
		String txtContent = FileUtils.readToString(srcFile);
		String firstLine = FileUtils.readFirstLine(srcFile);
		this.lineSize = (firstLine + SEPARATOR).length();
		int txtContentSize = txtContent.length();
		this.lines = txtContentSize / this.lineSize;
		System.out.println("######总行数为######" + lines);
		List<List<String>> data = new ArrayList<List<String>>();
		for (int i = 0; i < 50; i++) {
			data.add(new ArrayList<String>());
		}
		long freeM = (long) (Runtime.getRuntime().freeMemory() * 0.5 / 1024);
		System.out.println("######内存剩余######" + freeM + "M");
		splitData(data, txtContent, desFile);
	}

	private String list2String(List<String> strings) {
		if (strings == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
		}
		return sb.toString();
	}

	private static int get(String code) {
		char[] charArray = code.toCharArray();
		Integer total = 0;
		for (char c : charArray) {
			total += (int) c;
		}
		return Math.abs(total % 50);
	}

	private  void splitData(List<List<String>> data, String txtContent,
			String desFile) {
		int sepLength = System.getProperty("line.separator").length();
		for (int i = 0; i < this.lines; i++) {
			String line = txtContent.substring(i * this.lineSize, i * this.lineSize
					+ this.lineSize);
			String currentLine = line.substring(this.start, line.length() - sepLength);
			int i1 = get(currentLine);
			data.get(i1).add("," + this.code + "," + currentLine+"\n");
			if (i % 3000000 == 0) {
				saveData(data, desFile);
			}
		}
		saveData(data, desFile);
		System.out.println("######写入完成######");
	}

	private void saveData(List<List<String>> data, String desFile) {
		System.out.println("######写入文件######");
		for (int i = 0; i < data.size(); i++) {
			FileUtils.appendString(getName(desFile, i),
					list2String(data.get(i)));
			data.get(i).clear();
			System.gc();
		}
	}


	private static String getName(String desFile, int i) {
		File file = new File(desFile);
		if (!file.exists()) {
			file.mkdirs();
		}
		return desFile + "//" + i + ".txt";
	}
	
	public static void main(String[] args) {
		new SplitFile(400102, 18, 32).splitFile("D://codedata//ww2pi//ww22.txt","d://codedata//ww2pi//ww22",50);
	}
}
