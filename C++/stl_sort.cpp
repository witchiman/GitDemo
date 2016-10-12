#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

class T
{
public:
    int first, second;
    T(int x, int y):first(x),second(y){}

    bool operator < (const T& t) const     //第二个const不能少
    {
        return first<t.first;
    }
};

bool less_second(const T& t1, const T& t2)
{
    return t1.second <t2.second;
}

int main( )
{
    vector<T> v;
    for(int i=0;i<10; i++)
    {
        T t(10-i, i*3);
        v.push_back(t);
    }

    cout<<"未排序的数据为："<<endl;
    for(int i=0; i<v.size();i++)
       cout<<"("<<v[i].first<<", "<<v[i].second<<") ";
    cout<<endl<<endl;

    cout<<"按第一个数排序的数据为："<<endl;
    sort(v.begin(),v.end());                      //默认按重载的运算符'<'排序，必须实现
    for(int i=0; i<v.size();i++)
        cout<<"("<<v[i].first<<", "<<v[i].second<<") ";
    cout<<endl<<endl;

    cout<<"按第二个数排序的数据为："<<endl;
    sort(v.begin(),v.end(),less_second);
    for(int i=0; i<v.size();i++)
        cout<<"("<<v[i].first<<", "<<v[i].second<<") ";

    return 0;
}
