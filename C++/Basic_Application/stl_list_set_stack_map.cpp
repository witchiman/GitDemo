#include <iostream>
#include <list>
#include <set>
#include <stack>
#include <algorithm>
using namespace std;
void test_list();
void test_set();
void test_stack();

int main( )
{	
	test_list();
	test_set();
	test_stack();
    return 0;
}

void test_list()
{
	list<int> l;
    list<int>::const_iterator iter;
    l.push_back(5);
    l.push_back(3);
    l.push_back(4);
    l.push_back(6);
    l.push_back(2);

    for(iter=l.begin();iter!=l.end();++iter)
        cout<<*(iter)<<" ";
    cout<<endl<<endl;

    l.sort();   //µ÷ÓÃÄ¬ÈÏÅÅĞò
     for(iter=l.begin(); iter!=l.end(); ++iter)
        cout<<*(iter)<<" ";
    cout<<endl<<endl;

    l.sort(greater<int>());  //½µĞòÅÅÁĞ
     for(iter=l.begin();iter!=l.end();++iter)
        cout<<*(iter)<<" ";
    cout<<endl<<endl;
}

void test_set()
{ 
    set<string> str_set;
    set<string>::const_iterator iter;
    str_set.insert("banana");
    str_set.insert("orange");
    str_set.insert("grape");
    str_set.insert("watermelon");
    str_set.insert("apple");
    str_set.insert("pear");

    for(iter=str_set.begin();iter!=str_set.end();iter++)
        cout<<(*iter)<<endl;
  
}

void test_stack()
{
    stack<int> s;
    s.push(3);
    s.push(5);
    s.push(6);
    s.push(12);
    s.push(1);
    cout<<"The size of stack is "<<s.size()<<endl<<endl;;

    cout<<"The content is :"<<endl;
    int value;
    while(!s.empty())
    {
        value=s.top();
        cout<<value<<" ";
        s.pop();
    } 
}
