#include <iostream>
#include <list>
#include <set>
#include <stack>
#include <map>
#include <string>
#include <algorithm>
using namespace std;
void test_list();
void test_set();
void test_stack();
void test_map();

int main( )
{	
	test_list();
	test_set();
	test_stack();
	test_map();
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

    l.sort();   //调用默认排序
     for(iter=l.begin(); iter!=l.end(); ++iter)
        cout<<*(iter)<<" ";
    cout<<endl<<endl;

    l.sort(greater<int>());  //降序排列
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

void test_map()
{
	 map<string, int,less<string> > m;            //注意">>"间应有空格
    map<string, int,less<string> >::const_iterator iter;
    m["first"]=1;
    m["second"]=2;
    m["third"]=3;
    m["fourth"]=4;
    m["fifth"]=5;

    for(iter=m.begin();iter!=m.end();iter++)
        cout<<(*iter).first<<" is "<<(*iter).second<<" ";
    cout<<endl<<endl;

    iter=m.find("fourth");
    cout<<"The value is "<<(*iter).second<<endl;
}
