/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab02;

/**
 *
 * @author dylan
 */
class BaseClass  
{ 
    int data = 101; 
    public void print () 
    { 
        System.out.print ( data + "B\n" ); 
    } 
    public void fun () 
    { 
        print (); 
	System.out.print( data + "F\n" );
    } 
} 
class SubClass extends BaseClass  
{ 
    int data = 202; 
    public void print () 
    { 
        System.out.print ( data + "S\n" ); 
    } 
} 
public class Lab02  
{ 
    public static void main ( String[] args )  
    { 
        BaseClass obj = new SubClass (); 
        obj.print (); 
        obj.fun (); 
        System.out.print ( obj.data + "M\n" );
    } 
}
