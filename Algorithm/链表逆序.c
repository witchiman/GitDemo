 
#include <stdio.h> 

	typedef struct Node 
	{ 
		int data; 
		struct Node *next; 
	}Node; 
 
	//不带头结点的逆序算法
	void reverse1(Node *&L) 
	{ 	
		// 设单链表没有表头结点，L直接指示链表开始结点。链表全部逆转后，L指向原链尾结点，它成为新的开始结点
		if(L == NULL) return; 
		Node *p = L->next,*pr = NULL;  
		while(p)
		{ 
			L->next = pr;   //逆转L指针
			pr = L;  		
			L = p; 
			p = p->next; 	
		} 
		L->next = pr; 
	} 

	//带头结点的单链表逆序算法，类似于头插法
	void reverse2(Node *&L)
	{	
		if(L == NULL || L-next==NULL) return;
		
		Node *p = L->next, *q;
		L->next = NULL;			
		while(p)					//p结点始终指向旧的链表的开始结点
		{	q = p->next;			//q结点作为辅助结点来记录p的直接后继结点的位置
			p->next = L->next;		//将p所指结点插入新的链表中
			L->next = p;			
			p = q;					//因为后继结点已经存入q中，因此p仍然可以找到后继结点
		}
	}
 
List reverse3(List head)
{ 		
		List newHead;
		
        if(head==NULL ||head->next==NULL)
		{ 
               return head;
        } 
        newHead = reverse3(head->next); 
        head->next->next=head; 
        head->next=NULL; 
		
		return newHead;
} 