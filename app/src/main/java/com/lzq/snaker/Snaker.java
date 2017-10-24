package com.lzq.snaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;



public class Snaker {
	
	 private List<Point> list;
	 private int[][] a;
	 private int dX;
	 private int dY;
	 public Snaker() {
		// TODO Auto-generated constructor stub
		 a = new int[40][30];
		 list = new ArrayList<>();
		 list.add(new Point(20,5));
		 list.add(new Point(20,6));
		 list.add(new Point(20,7));
		 list.add(new Point(20,8));
		 list.add(new Point(20,9));
		 list.add(new Point(20,10));
		 list.add(new Point(20,11));
		 list.add(new Point(20,12));
		 list.add(new Point(20,13));
		 list.add(new Point(21,13));
		 list.add(new Point(22,13));
		 list.add(new Point(23,13));
		 list.add(new Point(24,13));
		 list.add(new Point(25,13));
		 list.add(new Point(26,13));
		 list.add(new Point(27,13));
		 list.add(new Point(28,13));
		 list.add(new Point(29,13));
		 list.add(new Point(29,14));
		 list.add(new Point(29,15));
		 list.add(new Point(29,16));
		 list.add(new Point(29,17));
	}
	public void clearView()
	{
		list.clear();
		list.add(new Point(20,5));
		 list.add(new Point(20,6));
		 list.add(new Point(20,7));
		 list.add(new Point(20,8));
		 list.add(new Point(20,9));
		 list.add(new Point(20,10));
		 list.add(new Point(20,11));
		 list.add(new Point(20,12));
		 list.add(new Point(20,13));
	}
	public  boolean right() {
		// TODO Auto-generated method stub
		list.remove(list.size()-1);
		Point point = new Point(list.get(0).x,list.get(0).y+1);
		list.add(0, point);
		if (impact(list.get(0).x,list.get(0).y)) {
			Point point1 = new Point(list.get(0).x,list.get(0).y+1);
			list.add(0, point1);
		}
		if (list.get(0).y>=a[0].length) {
			return false;
		}
		return true;
	}

	public void random()
	{
		Random randomX = new Random();
		Random randomY = new Random();
		dX = randomX.nextInt(40);
		dY = randomY.nextInt(30);
	}
	public  boolean impact(float x, float y) {
		// TODO Auto-generated method stub
		if(dX==x&&dY==y)
		{
			random();
			return true;
		}
		return false;
	}

	public  boolean left() {
		// TODO Auto-generated method stub
		list.remove(list.size()-1);
		Point point = new Point(list.get(0).x,list.get(0).y-1);
		list.add(0, point);
		if (impact(list.get(0).x,list.get(0).y)) {
			Point point1 = new Point(list.get(0).x,list.get(0).y-1);
			list.add(0, point1);
		}
		if (list.get(0).y<0) {
			return false;
		}
		return true;
	}

	public  boolean down() {
		// TODO Auto-generated method stub
		list.remove(list.size()-1);
		Point point = new Point(list.get(0).x+1,list.get(0).y);
		list.add(0, point);
		if (impact(list.get(0).x,list.get(0).y)) {
			Point point1 = new Point(list.get(0).x+1,list.get(0).y);
			list.add(0, point1);
		}
		if (list.get(0).x>=a.length) {
			return false;
		}
		return true;
	}

	public  boolean up() {
		// TODO Auto-generated method stub
		list.remove(list.size()-1);
		Point point = new Point(list.get(0).x-1,list.get(0).y);
		list.add(0, point);
		if (impact(list.get(0).x,list.get(0).y)) {
			Point point1 = new Point(list.get(0).x-1,list.get(0).y);
			list.add(0, point1);
		}
		if (list.get(0).x<0) {
			return false;
		}
		return true;
	}

	public int[][] init() {
		// TODO Auto-generated method stub
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				for (int j2 = 0; j2 < list.size(); j2++) {
					if ((i==list.get(j2).x&&j==list.get(j2).y)||(i==dX&&j==dY)) {
						a[i][j]=1;
						break;
						
					}else
					{
						a[i][j]=0;
					}
				}
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
		return a;
		
		
	}
	
}
