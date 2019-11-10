/**
 * 
 */
package com.soring.bbsVO;

import java.sql.Timestamp;

/**
 * @CLASS Name
 *  Bvo
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-10-28
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.soring.bbsVO
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class Bvo {
	// DB에서 가져오는 객체(컬럼들) 만들기
	int bNO_BBS;
	String bNM_BBS;
	Timestamp bDT_BBS;
	String bSUBJECT;
	String bCONTENT;
	int bHIT;
	int bGROUP;
	int bSTEP;
	int bINDENT;
	
	public Bvo() {
		
	}
	
	public Bvo(int bNO_BBS, String bNM_BBS, Timestamp bDT_BBS, String bSUBJECT, String bCONTENT, int bHIT, int bGROUP, int bSTEP, int bINDENT) {
		this.bNO_BBS = bNO_BBS;
		this.bNM_BBS = bNM_BBS;
		this.bDT_BBS = bDT_BBS;
		this.bSUBJECT = bSUBJECT;
		this.bCONTENT = bCONTENT;
		this.bHIT = bHIT;
		this.bGROUP = bGROUP;
		this.bSTEP = bSTEP;
		this.bINDENT = bINDENT;
	}//인자생성자

	//[region] setter, getter
	
	/**
	 * @return the bNO_BBS
	 */
	public int getbNO_BBS() {
		return bNO_BBS;
	}

	/**
	 * @param bNO_BBS the bNO_BBS to set
	 */
	public void setbNO_BBS(int bNO_BBS) {
		this.bNO_BBS = bNO_BBS;
	}

	/**
	 * @return the bNM_BBS
	 */
	public String getbNM_BBS() {
		return bNM_BBS;
	}

	/**
	 * @param bNM_BBS the bNM_BBS to set
	 */
	public void setbNM_BBS(String bNM_BBS) {
		this.bNM_BBS = bNM_BBS;
	}

	/**
	 * @return the bDT_BBS
	 */
	public Timestamp getbDT_BBS() {
		return bDT_BBS;
	}

	/**
	 * @param bDT_BBS the bDT_BBS to set
	 */
	public void setbDT_BBS(Timestamp bDT_BBS) {
		this.bDT_BBS = bDT_BBS;
	}

	/**
	 * @return the bSUBJECT
	 */
	public String getbSUBJECT() {
		return bSUBJECT;
	}

	/**
	 * @param bSUBJECT the bSUBJECT to set
	 */
	public void setbSUBJECT(String bSUBJECT) {
		this.bSUBJECT = bSUBJECT;
	}

	/**
	 * @return the bCONTENT
	 */
	public String getbCONTENT() {
		return bCONTENT;
	}

	/**
	 * @param bCONTENT the bCONTENT to set
	 */
	public void setbCONTENT(String bCONTENT) {
		this.bCONTENT = bCONTENT;
	}

	/**
	 * @return the bHIT
	 */
	public int getbHIT() {
		return bHIT;
	}

	/**
	 * @param bHIT the bHIT to set
	 */
	public void setbHIT(int bHIT) {
		this.bHIT = bHIT;
	}

	/**
	 * @return the bGROUP
	 */
	public int getbGROUP() {
		return bGROUP;
	}

	/**
	 * @param bGROUP the bGROUP to set
	 */
	public void setbGROUP(int bGROUP) {
		this.bGROUP = bGROUP;
	}

	/**
	 * @return the bSTEP
	 */
	public int getbSTEP() {
		return bSTEP;
	}

	/**
	 * @param bSTEP the bSTEP to set
	 */
	public void setbSTEP(int bSTEP) {
		this.bSTEP = bSTEP;
	}

	/**
	 * @return the bINDENT
	 */
	public int getbINDENT() {
		return bINDENT;
	}

	/**
	 * @param bINDENT the bINDENT to set
	 */
	public void setbINDENT(int bINDENT) {
		this.bINDENT = bINDENT;
	}
	
	//[end]
}
