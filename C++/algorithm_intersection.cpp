/**
 *求两个链表的交集
 **
 */

#include <iostream>
#include <stdlib.h>
using namespace std;

typedef struct node
{
    int elem;
    struct node *next;
};

/*初始化列表,传入一个空链表和数组*/
void initlist(node *&l, int a[], int n)
{
    int i;
    node *tail;  //采用尾插法
    for(i=0; i<n; i++)
    {
        node *p = (node *)malloc(sizeof(node));
        if(p == NULL)
        {
            cout<< "申请内容空间失败！"<<endl;;
            break;
        }
        p->elem = a[i];
        p->next = NULL;
        if(l == NULL)
        {
            l = p;    //l指向第一个元素
            tail = l;
        }
        else
        {
            tail->next = p;
            tail = p;
        }

    }

}

/*求交集*/
void intersection(node *&La, node *Lb)
{
    node *pa,*pb,*pre,*q;
    pa = La;
    pre = NULL;

    while(pa)
    {
        pb = Lb;
        while(pb && (pa->elem != pb->elem))
              pb = pb->next;
        if(pb)  //有相同元素
        {
            if(!pre)  //La中开始的元素为共同元素
                La = pa->next;
            else
                pre->next = pa->next;

            q = pa;      //删除共同元素
            pa = pa->next;
            free(q);
        }
        else             //La中的的一个元素逐一与Lb比较完，其下一个元素开始逐一与Lb中的元素比较
        {
            pre = pa;
            pa = pa->next;
        }
    }
}


int main( )
{
    int a[]= {5, 10, 20, 15, 25, 30};
    int b[] = {5, 15, 35, 25};
    node *la = NULL;
    node *lb = NULL;
    
    initlist(la, a, sizeof(a)/sizeof(int));
    initlist(lb, b, sizeof(b)/sizeof(int));

    intersection(la, lb);
    
	node *p;
    p = la;
    while(p)  //输出处理后的La
    {
        cout<<p->elem<<" ";
        p = p->next;
    }
    return 0;
}



