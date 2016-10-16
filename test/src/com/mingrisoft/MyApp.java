package com.mingrisoft;

import java.util.ArrayList;
import java.util.Scanner;

public class MyApp {
	ArrayList<String> Polynomial = new ArrayList<String>();//用于存储输入的表达式
	ArrayList<String> Operators = new ArrayList<String>();//用于存储分隔的相应加减号
	

	public boolean expression(String str){//对输入的字符串进行处理，若存在非法字符则返回false
    	String num = "";//作为中间字符串，将多项式以加号为分隔
    	int begin=0;
    	if(str.charAt(0)=='-')//处理字符串开头是负号的情况
    	{
    		Polynomial.add("0");
    		Operators.add("-");
    		begin=1;
    	}
    	for(int i=0;i<str.length()-1; i++)//判断不能有运算符连续出现
    	{
			if((str.charAt(i)=='+') || (str.charAt(i)=='-')||(str.charAt(i)=='.')||(str.charAt(i)=='*'))
			{
				if((str.charAt(i+1)=='+') || (str.charAt(i+1)=='-')||(str.charAt(i+1)=='.')||(str.charAt(i+1)=='*'))
					return false;
			}
		}
    	for(int i=begin;i<str.length(); i++)
    	{
    		if((str.charAt(i)=='+') || (str.charAt(i)=='-'))
    		{
    			if(num=="")
    				return false;
    			else
    			{
    				Polynomial.add(num);
    				num="";
    				if(str.charAt(i)=='+')
    					Operators.add("+");
    				else
    					Operators.add("-");
    			}
    		}
    		else
    		{
    			if((str.charAt(i)=='*') || (str.charAt(i)=='.') || ((str.charAt(i)>='0') && (str.charAt(i)<='9')) || ((str.charAt(i)>='A') && (str.charAt(i)<='Z')) || ((str.charAt(i)>='a') && (str.charAt(i)<='z')))
    			{
    				num=num+str.charAt(i);
    				if(i==(str.length()-1))
    					Polynomial.add(num);
    			}
    			else
    				return false;//存在非法字符时
    		}
    	}
    	return true;
	}
	
	
	
	public String simplify(String x,String value){//求值简化函数,返回赋值简化之后的表达式
		ArrayList<String> temporary = new ArrayList<String>();//用于临时存储
		for(int i=0;i<Polynomial.size();i++)
		{
			String substring=Polynomial.get(i);
			String in="";
			String mun="";
			for(int j=0;j<substring.length();j++)
			{
				if(substring.charAt(j)=='*')
				{
					if(mun.equals(x))
						mun=value;
					in=in+mun;
					in=in+'*';
					mun="";
				}
				else
				{
					mun=mun+substring.charAt(j);
					if(j==substring.length()-1)
					{
						if(mun.equals(x))
							mun=value;
						in=in+mun;
					}
				}
			}
			
	        in=calculate(in);//调用表达式计算函数，计算乘法
	     
			temporary.add(in);
		}
		String out="";//计算后输出的字符串
		double number=0;//计算后的数值部分
		for(int m=0;m<temporary.size();m++)
		{
			if(isnumber(temporary.get(m)))
			{
				if(m==0)
					number=Double.parseDouble(temporary.get(m));
				else
				{
					if(Operators.get(m-1)=="+")
						number=number+Double.parseDouble(temporary.get(m));
					else
						number=number-Double.parseDouble(temporary.get(m));
				}
			}
			else
			{
				if(m==0)
				{
					out="+";
					out=out+temporary.get(m);
				}
				else
				{
					out=out+Operators.get(m-1);
					out=out+temporary.get(m);
				}
					
			}
		}
		if(number!=0)
		     out=String.valueOf(number)+out;
		else
		{
			if(out.charAt(0)=='+')
			{
				String out1="";
				for(int i=1;i<out.length();i++)
					out1=out1+out.charAt(i);
				out=out1;
			}
		}
		
		return out;
	}
	
	
	
    	public boolean derivative(String x){//求导函数
    		ArrayList<String> a = new ArrayList<String>();//用于临时存储
    		ArrayList<String> ao = new ArrayList<String>();//用于临时存储对应的前面的操作符
    		for(int i=0;i<Polynomial.size();i++)
    		{
    			String temp="";
    			temp=QiuDao(Polynomial.get(i),x);
    			if(!temp.equals(""))
    			{	
    				a.add(temp);
    				if(i==0)
    					ao.add("+");
    				else
    			        ao.add(Operators.get(i-1));
    			}
    		}
    		
    		
    		String out="";//计算后输出的字符串
    		double number=0;//计算后的数值部分
    		for(int m=0;m<a.size();m++)
    		{
    			if(isnumber(a.get(m)))
    			{
    				if(m==0)
    					number=Double.parseDouble(a.get(m));
    				else
    				{
    					if(ao.get(m)=="+")
    						number=number+Double.parseDouble(a.get(m));
    					else
    						number=number-Double.parseDouble(a.get(m));
    				}
    			}
    			else
    			{
    				/*if(m==0)
    				{
    					out="+";
    					out=out+a.get(m);
    				}
    				else
    				{*/
    					out=out+ao.get(m);
    					out=out+a.get(m);
    				
    					
    			}
    		}
    		if(number!=0)
   		        out=String.valueOf(number)+out;
   		    else
   		   {
   			    if(out.charAt(0)=='+')
   			    {
   				    String out1="";
   				    for(int i=1;i<out.length();i++)
   					    out1=out1+out.charAt(i);
   				    out=out1;
   			    }
   		   }
    		System.out.println("求导后结果为："+out);
    		return true;
    }
    	
    	
    	public String QiuDao(String a,String x){//对字符串a进行求导处理
    		ArrayList<String> ai = new ArrayList<String>();//用于临时存储
    		double xi=1;//系数
    		int number=0;//指数
    		String num="";
    		for(int i=0;i<a.length();i++)
    		{
    			if(a.charAt(i)=='*')
    			{
    				if(num.equals(x))
    					number++;
    				else if(isnumber(num))
    					xi=xi*Double.parseDouble(num);
    				else
    					ai.add(num);
    				num="";
       			}
    			else
    			{
    				num=num+a.charAt(i);
    				if(i==a.length()-1)
    				{
    					if(num.equals(x))
        					number++;
        				else if(isnumber(num))
        					xi=xi*Double.parseDouble(num);
        				else
        					ai.add(num);
    				}
    				
    					
    			}
    			
    		}
    		if(number==0)
    			return "";
    		else
    		{
    			String re="";
    			int flag=0;
    			if((xi*number)!=1)
    			    {
    				    re=re+String.valueOf(xi*number);
    			        flag=1;
    			    }
    			else
    			{
    				if(number==1 && ai.size()==0)
    				{
    					re=re+String.valueOf(xi*number);
    					flag=1;
    				}
    			}
    			
    			
    				for(int i=1;i<number;i++)
    				{
    					if(i==1 && flag==0)
    						re=re+x;
    					else
    					{
    						re=re+"*";
    					    re=re+x;
    					}
    				}
    			     
    			
    			for(int i=0;i<ai.size();i++)
    			{
    				if(i==0 && flag==0)
    					re=re+ai.get(i);
    				else
    				{    re=re+"*";
    				     re=re+ai.get(i);
    				}
    			}
    			return re;	
    		}
    		
    	}
	public String calculate(String in){//计算表达式中的乘法的函数
		ArrayList<Double> a = new ArrayList<Double>();//用于存储串中的数
		ArrayList<String> b = new ArrayList<String>();//用于存储串中的变量名
		String mun="";
		int flag=0;
		for(int i=0;i<in.length();i++)
		{
			if(in.charAt(i)=='*')
			{
				if(flag==0)
					a.add(Double.parseDouble(mun));
				else
					b.add(mun);
				flag=0;
				mun="";
			}
			else
			{
				if((in.charAt(i)>='0' && in.charAt(i)<='9') || in.charAt(i)=='.')
					mun=mun+in.charAt(i);
				else
				{
					mun=mun+in.charAt(i);
					flag=1;
				}
				if(i==in.length()-1)
				{
					if(flag==0)
						a.add(Double.parseDouble(mun));
					else
						b.add(mun);
					flag=0;
				}
			}
		}
		double nj=1.0;//用于存储系数
		for(int j=0;j<a.size();j++)
		{
			nj=nj*a.get(j);
		}
		
		
		String re="";
		int flagi=0;
		if((nj)!=1)
		    {
			    re=re+String.valueOf(nj);
		        flagi=1;
		    }
		else
		{
			if(nj==1 && b.size()==0)
			{
				re=re+String.valueOf(nj);
				flagi=1;
			}
		}
		
		
			
		     
		
		for(int i=0;i<b.size();i++)
		{
			if(i==0 && flagi==0)
				re=re+b.get(i);
			else
			{    re=re+"*";
			     re=re+b.get(i);
			}
		}
		return re;	
	}
	
	
	
	public boolean isnumber(String a){//判断字符串a是否可以转换成数字
		for(int i=0;i<a.length();i++){
			if((a.charAt(i)>='0' && a.charAt(i)<='9') || a.charAt(i)=='.')
				continue;
			else
				return false;
		}
		return true;
	}
	
	
	
	public boolean ex(String a,String bian){//判断变量bian是否出现在表达式a中
		if(a.indexOf(bian)>=0)
			return true;
		else
			return false;		
	}
	
	public static void main(String[] args){
		MyApp a=new MyApp();
		boolean b;
		Scanner s=new Scanner(System.in);
		String str;
    	str=s.nextLine();//以字符串的形式读入多项式
    	double time1 = System.currentTimeMillis();
    	System.out.println("处理字符串开始的时间为:"+time1/60+"秒");
    	String str_o="";
    	int f=0;
    	for(int i=0;i<str.length();i++)//处理空格和制表符
    	{
    		if(str.charAt(i)==' ' || str.charAt(i)=='\t')
    		{
    			f=1;
    			continue;
    		}
    		else
    			str_o=str_o+str.charAt(i);
    	}
    	if(f==0)
		    b=a.expression(str);
    	else
    		b=a.expression(str_o);
		if(b)
		{
		
			System.out.println(str);
		}
		else
			System.out.println("您输入的表达式不合法！");
		double time2 = System.currentTimeMillis();
    	System.out.println("处理字符串结束的时间为:"+time2/60+"秒");
    	double time3 = time2-time1;
    	System.out.println("处理字符串的总时间为:"+time3/60+"秒");
		
		String order1="!simplify ";
		String order2="!d/d";
		String order;
		order=s.nextLine();//以字符串的形式读入命令
		while(!order.equals("end"))//输入end时退出程序
		{
			if(order.charAt(1)=='d')
			{
				double time4 = System.currentTimeMillis();
		    	System.out.println("开始执行指令的时间为:"+time4/60+"秒");
				if(order.length()<=4)
				{
					System.out.println("非法指令！");
					break;
				}
				else
				{
					int j=0;
					for(int i=0;i<4;i++)
					{
						if(order.charAt(i)!=order2.charAt(i))
						{
							j=1;
							break;
						}
					}
					if(j==1)
					{
						System.out.println("非法指令！");
						break;
					}
					else
					{
						String bian="";
						for(int i=4;i<order.length();i++)
						{
							bian=bian+order.charAt(i);
						}
						//***********************判断变量bian是否在表达式中出现**************************
						if(f==0)
						{
							if(a.ex(str,bian))
							{
								if(!a.derivative(bian))
						    		System.out.println("非法的输入！");
								else
								{
									double time5 = System.currentTimeMillis();
									System.out.println("指令完成结束的时间为:"+time5/60+"秒");
									double time6 = time5-time4;
									System.out.println("指令执行的总时间为:"+time6/60+"秒");
								}
							}
							else
								System.out.println("不存在此变量！");
						}
				    	else
				    	{
				    		if(a.ex(str_o,bian))
							{
								if(!a.derivative(bian))
						    		System.out.println("非法的输入！");
								else
								{
									double time5 = System.currentTimeMillis();
									System.out.println("指令完成结束的时间为:"+time5/60+"秒");
									double time6 = time5-time4;
									System.out.println("指令执行的总时间为:"+time6/60+"秒");
								}
							}
							else
								System.out.println("不存在此变量！");
				    	}
				    		
					}
				}
			}
			else if(order.charAt(1)=='s')
			{
				double time4 = System.currentTimeMillis();
		    	System.out.println("开始执行指令的时间为:"+time4/60+"秒");
				if(order.length()<9)
				{
					System.out.println("非法指令！");
					break;
				}
				else
				{
					int j=0;
					for(int i=0;i<9;i++)
					{
						if(order.charAt(i)!=order1.charAt(i))
						{
							j=1;
							break;
						}
					}
					if(j==1)
					{
						System.out.println("非法指令！");
						break;
					}
					else
					{
						if(order.length()==9)//如果simplify后未指定相应的变量及取值，则直接输出原字符串
						{
							if(f==0)
								System.out.println(str);
					    	else
					    		System.out.println(str_o);
						}
						else
						{
							if(order.charAt(9)!=' ')
								System.out.println("非法指令！");
							else
							{
//								
								int flag3=0;//设置标志变量，用于分辨此时读入的是变量名还是变量的值
								String x="";//变量
					    	    String value="";//变量值
					    	    ArrayList<String> bian_x = new ArrayList<String>();//用于存储变量
					    	    ArrayList<String> bian_value = new ArrayList<String>();//用于存储相应变量的值
								for(int i=10;i<order.length();i++)
								{
									if(order.charAt(i)=='=')
									{
										bian_x.add(x);
										x="";
										flag3=1;
									}
									else if(order.charAt(i)==' ')
									{
										bian_value.add(value);
										value="";
										flag3=0;
									}
									else
									{
										if(flag3==0)
											x=x+order.charAt(i);
										else
										{
											value=value+order.charAt(i);
											if(i==order.length()-1)
												bian_value.add(value);
										}
									}
								}
								String fanhui="";//用于存储返回的字符串
								int flagg=0;//标志变量
								for(int i=0;i<bian_x.size();i++)
								{
									MyApp k=new MyApp();
									int flag=0;//标志变量
									if(f==0)
									{
										if(!a.ex(str,bian_x.get(i)))//如果不存在此变量
										{
											flag=1;
										}
										
									}
							    	else
							    	{
							    		if(!a.ex(str_o,bian_x.get(i)))
										{
										    flag=1;
										}
							    	}
									if(flag==1)
									{
										System.out.println("不存在变量"+bian_x.get(i));
										flagg=1;
										break;//设置标志以退出
									}
									else
									{
										if(i==0)
											fanhui=fanhui+a.simplify(bian_x.get(i),bian_value.get(i));
										else
										{
											k.expression(fanhui);
											fanhui=k.simplify(bian_x.get(i),bian_value.get(i));
										}
									}
								}
								if(flagg==0)
								{
									System.out.println(fanhui);
									double time5 = System.currentTimeMillis();
									System.out.println("this指令完成结束的时间为:"+time5/60+"秒");
									double time6 = time5-time4;
									System.out.println("指令执行的总时间为:"+time6/30+"秒");
								}
								
							}
						  }
						}
					}
				}
				else
					System.out.println("非法指令！");
				order=s.nextLine();//以字符串的形式读入命令
		
			}
	}
}
