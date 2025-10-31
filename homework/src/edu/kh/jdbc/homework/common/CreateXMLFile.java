package edu.kh.jdbc.homework.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	
	public static void main(String[] args) {
		
		FileOutputStream fos = null;
		Scanner sc = null;
		
		try {
			
			sc = new Scanner(System.in);
			
			System.out.print("파일 이름 입력: ");
			String fileName = sc.next();
			
			fos = new FileOutputStream(fileName + ".xml");
			
			Properties prop = new Properties();
			
			prop.storeToXML(fos, fileName + ".xml 파일");
		
			System.out.println(fileName + " 파일 생성 완료!");
			
		} catch (Exception e) {
			
			System.out.println("파일 생성 중 예외 발생!");
			
		} finally {
			
				try {
					
					if(fos != null) fos.close();
					if(sc != null) sc.close();
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
		}
		
		
	}
	
}
