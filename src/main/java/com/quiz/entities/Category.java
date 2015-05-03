package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Categories")
public class Category
{

	/*
	 * CREATE TABLE Categories(

		categoryid			INTEGER(10),
		categoryname		VARCHAR(100),
		categorydescription	VARCHAR(100),

		PRIMARY KEY(categoryid));
	 */


	/*-----------------------*/
	
	private Integer categoryid;
	private String categoryname;
	private String categorydescription;
	
	
	/*-----------------------*/
	
	@Id
	@Column(name = "categoryid", unique = true, nullable = false)
	public Integer getCategoryid() 
	{
		return categoryid;
	}
	
	public void setCategoryid(Integer categoryid) 
	{
		this.categoryid = categoryid;
	}
	
	@Column(name = "categoryname", unique = true, nullable = false)
	public String getCategoryname()
	{
		return categoryname;
	}
	public void setCategoryname(String categoryname)
	{
		this.categoryname = categoryname;
	}
	
	@Column(name = "categorydescription", unique = false, nullable = true)
	public String getCategorydescription() 
	{
		return categorydescription;
	}
	public void setCategorydescription(String categorydescription) 
	{
		this.categorydescription = categorydescription;
	}
	
	/*-----------------------*/
}
