package com.agitation.sport.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;


/** 
 * 对分页的基本数据进行一个简单的封装 
 */
public class MyPage<T> implements Page<T>{

	private int number = 1;//页码，默认是第一页  
    private int size = 15;//每页显示的记录数，默认是15  
    private int total;//总记录数  
	private int totalPages;//总页数  
    private List<T> content;//对应的当前页记录  
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它分装成一个Map对象  
   
    public MyPage(){
    	
    }
    
    public MyPage(Map<String, Object> params){
    	this.params=params;
    	this.number=1;
    }
    
    public MyPage(Map<String, Object> params, int number){
    	this.params=params;
    	this.number=number;
    }
    
   
   
   
    public void setTotal(int total) {  
       this.total = total;  
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。  
       int totalPage = total%size==0 ? total/size : total/size + 1;  
       this.setTotalPages(totalPage);  
    }  
     
    public Map<String, Object> getParams() {  
       return params;  
    }  
     
    public void setParams(Map<String, Object> params) {  
       this.params = params;  
    }  

	@Override
	public List<T> getContent() {
		return content;
	}

	@Override
	public int getNumber() {
		return number;
	}
	
	public int getPage() {
		return number;
	}

	@Override
	public int getNumberOfElements() {
		return 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public long getTotalElements() {
		return 0;
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public boolean hasContent() {
		return content!=null && content.size()>0;
	}

	@Override
	public boolean hasNextPage() {
		return number<totalPages;
	}

	@Override
	public boolean hasPreviousPage() {
		return number>1;
	}

	@Override
	public boolean isFirstPage() {
		return number==1;
	}

	@Override
	public boolean isLastPage() {
		return number==totalPages;
	}

	@Override
	public Iterator iterator() {
		return null;
	}

	@Override
	public Pageable nextPageable() {
		return null;
	}

	@Override
	public Pageable previousPageable() {
		return null;
	}
	
	public int getTotal() {
		return total;
	}
	public void setPage(int number) {
		this.number = number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public ShiroUser getUser(){
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLast() {
		// TODO Auto-generated method stub
		return false;
	}
}
