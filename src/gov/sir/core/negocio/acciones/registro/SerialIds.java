/*
 * Created on 13-nov-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.acciones.registro;

import java.util.StringTokenizer;

/**
 * @author dsalas
 *
 * Esta clase permite recorrer seriales
 */
public class SerialIds {

	private boolean serialExists;
	private int nmLeft;
	private int nmRight;
	private int curr;
	private String group;

	/**
	 * Construye la instancia a partir del caption de serial
	 * @param caption
	 */
	public SerialIds(String caption){
		try{
			StringTokenizer stCaption = new StringTokenizer(caption,":");
			String rango=stCaption.nextToken();//RANGO 0
			if(!rango.equals("RANGO"))
				throw new Exception("No es un rango "+caption);
			//CODIGOS
			stCaption.nextToken();//1
			String idLeft=stCaption.nextToken();//2
			stCaption.nextToken();//3
			String idRight=stCaption.nextToken();//4
			//CREAR TOKENIZADORES
			StringTokenizer stLeft=new StringTokenizer(idLeft,"-");
			StringTokenizer stRight=new StringTokenizer(idRight,"-");
			//OBTENER CODIGO DE GRUPO
			String grLeft=stLeft.nextToken();
			String grRight=stRight.nextToken();
			group=grLeft;
			//OBTENER CODIGO DE SERIAL
			String srLeft=stLeft.nextToken();
			String srRight=stRight.nextToken();
			//CONVERTIR A ENTEROS
			nmLeft=Integer.parseInt(srLeft);
			nmRight=Integer.parseInt(srRight);
			curr=nmLeft;
			//SI PERTENECEN AL MISMO GRUPO Y RIGHT ES MAYOR O IGUAL A LEFT
			serialExists=(grLeft.equals(grRight)&&nmRight>=nmLeft);
		}catch(Exception e){
			serialExists=false;
		}
	}

	/**
	 * Construye una instancia
	 * @param idLeft
	 * @param idRight
	 */
	public SerialIds(String idLeft, String idRight){
		try{
			//CREAR TOKENIZADORES
			StringTokenizer stLeft=new StringTokenizer(idLeft,"-");
			StringTokenizer stRight=new StringTokenizer(idRight,"-");
			//OBTENER CODIGO DE GRUPO
			String grLeft=stLeft.nextToken();
			String grRight=stRight.nextToken();
			group=grLeft;
			//OBTENER CODIGO DE SERIAL
			String srLeft=stLeft.nextToken();
			String srRight=stRight.nextToken();
			//CONVERTIR A ENTEROS

			nmLeft=Integer.parseInt(srLeft);
			nmRight=Integer.parseInt(srRight);
		
			curr=nmLeft;
			//SI PERTENECEN AL MISMO GRUPO Y RIGHT ES MAYOR O IGUAL A LEFT
			serialExists=(grLeft.equals(grRight)&&nmRight>=nmLeft);
		} catch(Exception e){
			serialExists=false;
		}
	}
	
	public boolean hasNext(){
		return (serialExists && (curr <= nmRight));
	}
	
	public String nextValue(){
		int auxCurr=curr;
		curr++;
		return group+"-"+String.valueOf(auxCurr);
	}

	/**
	 * @return
	 */
	public boolean isSerialExists() {
		return serialExists;
	}

}
