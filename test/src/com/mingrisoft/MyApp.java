package com.mingrisoft;
import java.util.ArrayList;
import java.util.Scanner;
public class MyApp {
    final ArrayList<String> Polynomial = new ArrayList<String>();//���ڴ洢����ı��ʽ
	ArrayList<String> Operators = new ArrayList<String>();//���ڴ洢�ָ�����Ӧ�Ӽ���
	public boolean expression(String str){//��������ַ������д��������ڷǷ��ַ��򷵻�false
    	String num = "";//��Ϊ�м��ַ�����������ʽ�ԼӺ�Ϊ�ָ�
    	int begin=0;
    	if(str.charAt(0)=='-')//�����ַ�����ͷ�Ǹ��ŵ����
    	{
    		Polynomial.add("0");
    		Operators.add("-");
    		begin=1;
    	}
    	for(int i=0;i<str.length()-1; i++)//�жϲ������������������
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
    				return false;//���ڷǷ��ַ�ʱ
    		}
    	}
    	return true;
	}
	public String simplify(String x,String value){//��ֵ�򻯺���,���ظ�ֵ��֮��ı��ʽ
		ArrayList<String> temporary = new ArrayList<String>();//������ʱ�洢
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
			
	        in=calculate(in);//���ñ��ʽ���㺯��������˷�
			temporary.add(in);
		}
		String out="";//�����������ַ���
		double number=0;//��������ֵ����
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
    	public boolean derivative(String x){//�󵼺���
    		ArrayList<String> a = new ArrayList<String>();//������ʱ�洢
    		ArrayList<String> ao = new ArrayList<String>();//������ʱ�洢��Ӧ��ǰ��Ĳ�����
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
    		String out="";//�����������ַ���
    		double number=0;//��������ֵ����
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
    		System.out.println("�󵼺���Ϊ��"+out);
    		return true;
    }	
    	public String QiuDao(String a,String x){//���ַ���a�����󵼴���
    		ArrayList<String> ai = new ArrayList<String>();//������ʱ�洢
    		double xi=1;//ϵ��
    		int number=0;//ָ��
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
	public String calculate(String in){//������ʽ�еĳ˷��ĺ���
		ArrayList<Double> a = new ArrayList<Double>();//���ڴ洢���е���
		ArrayList<String> b = new ArrayList<String>();//���ڴ洢���еı�����
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
		double nj=1.0;//���ڴ洢ϵ��
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

	public boolean isnumber(String a){//�ж��ַ���a�Ƿ����ת��������
		for(int i=0;i<a.length();i++){
			if((a.charAt(i)>='0' && a.charAt(i)<='9') || a.charAt(i)=='.')
				continue;
			else
				return false;
		}
		return true;
	}
	public boolean ex(String a,String bian){//�жϱ���bian�Ƿ�����ڱ��ʽa��
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
    	str=s.nextLine();//���ַ�������ʽ�������ʽ
    	double time1 = System.currentTimeMillis();
    	System.out.println("�����ַ�����ʼ��ʱ��Ϊ:"+time1/60+"��");
    	String str_o="";
    	int f=0;
    	for(int i=0;i<str.length();i++)//����ո���Ʊ��
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
			System.out.println("������ı��ʽ���Ϸ���");
		double time2 = System.currentTimeMillis();
    	System.out.println("�����ַ���������ʱ��Ϊ:"+time2/60+"��");
    	double time3 = time2-time1;
    	System.out.println("�����ַ�������ʱ��Ϊ:"+time3/60+"��");
		
		String order1="!simplify ";
		String order2="!d/d";
		String order;
		order=s.nextLine();//���ַ�������ʽ��������
		while(!order.equals("end"))//����endʱ�˳�����
		{
			if(order.charAt(1)=='d')
			{
				double time4 = System.currentTimeMillis();
		    	System.out.println("��ʼִ��ָ���ʱ��Ϊ:"+time4/60+"��");
				if(order.length()<=4)
				{
					System.out.println("�Ƿ�ָ�");
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
						System.out.println("�Ƿ�ָ�");
						break;
					}
					else
					{
						String bian="";
						for(int i=4;i<order.length();i++)
						{
							bian=bian+order.charAt(i);
						}
						//***********************�жϱ���bian�Ƿ��ڱ��ʽ�г���**************************
						if(f==0)
						{
							if(a.ex(str,bian))
							{
								if(!a.derivative(bian))
						    		System.out.println("�Ƿ������룡");
								else
								{
									double time5 = System.currentTimeMillis();
									System.out.println("ָ����ɽ�����ʱ��Ϊ:"+time5/60+"��");
									double time6 = time5-time4;
									System.out.println("ָ��ִ�е���ʱ��Ϊ:"+time6/60+"��");
								}
							}
							else
								System.out.println("�����ڴ˱�����");
						}
				    	else
				    	{
				    		if(a.ex(str_o,bian))
							{
								if(!a.derivative(bian))
						    		System.out.println("�Ƿ������룡");
								else
								{
									double time5 = System.currentTimeMillis();
									System.out.println("ָ����ɽ�����ʱ��Ϊ:"+time5/60+"��");
									double time6 = time5-time4;
									System.out.println("ָ��ִ�е���ʱ��Ϊ:"+time6/60+"��");
								}
							}
							else
								System.out.println("�����ڴ˱�����");
				    	}
				    		
					}
				}
			}
			else if(order.charAt(1)=='s')
			{
				double time4 = System.currentTimeMillis();
		    	System.out.println("��ʼִ��ָ���ʱ��Ϊ:"+time4/60+"��");
				if(order.length()<9)
				{
					System.out.println("�Ƿ�ָ�");
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
						System.out.println("�Ƿ�ָ�");
						break;
					}
					else
					{
						if(order.length()==9)//���simplify��δָ����Ӧ�ı�����ȡֵ����ֱ�����ԭ�ַ���
						{
							if(f==0)
								System.out.println(str);
					    	else
					    		System.out.println(str_o);
						}
						else
						{
							if(order.charAt(9)!=' ')
								System.out.println("�Ƿ�ָ�");
							else
							{
//								
								int flag3=0;//���ñ�־���������ڷֱ��ʱ������Ǳ��������Ǳ�����ֵ
								String x="";//����
					    	    String value="";//����ֵ
					    	    ArrayList<String> bian_x = new ArrayList<String>();//���ڴ洢����
					    	    ArrayList<String> bian_value = new ArrayList<String>();//���ڴ洢��Ӧ������ֵ
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
								String fanhui="";//���ڴ洢���ص��ַ���
								int flagg=0;//��־����
								for(int i=0;i<bian_x.size();i++)
								{
									MyApp k=new MyApp();
									int flag=0;//��־����
									if(f==0)
									{
										if(!a.ex(str,bian_x.get(i)))//��������ڴ˱���
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
										System.out.println("�����ڱ���"+bian_x.get(i));
										flagg=1;
										break;//���ñ�־���˳�
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
									System.out.println("thisָ����ɽ�����ʱ��Ϊ:"+time5/60+"��");
									double time6 = time5-time4;
									System.out.println("ָ��ִ�е���ʱ��Ϊ:"+time6/30+"��");
								}
								
							}
						  }
						}
					}
				}
				else
					System.out.println("�Ƿ�ָ�������������");
				order=s.nextLine();//���ַ�������ʽ��������
		
			}
	}
}
