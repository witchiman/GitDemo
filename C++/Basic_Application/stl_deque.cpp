#include <iostream>
#include <deque>
using namespace std;
void show(const deque<int>& d);

int main( )
{
    deque<int> d(10,6);  //初始化个数为10的队列，数据都为6
    show(d);

    d.push_back(2);
    show(d);

    d.push_front(3);
    show(d);

    d.insert(d.begin()+1,4,5);  //从index为1的位置开始插入4个5
    show(d);
    cout<<"第10个位置的数为："<<d.at(10)<<endl<<endl;

    d.pop_front();      //移除最前和最后的元素
    d.pop_back();
    cout<<"去除首尾后";show(d);

    d.assign(8,25);    //重新赋值成含有8个25的队列
    show(d);

    d.push_front(2);
    d.push_front(3);
    d.erase(d.begin()+1);   //删除第2个元素
    show(d);

    return 0;
}

void show(const deque<int>& d)
{
    deque<int>::const_iterator iter;
    cout<<"队列内容为："<<endl;
    for(iter=d.begin();iter!=d.end(); ++iter)
        cout<<*iter<<" ";
    cout<<endl<<endl;
}
