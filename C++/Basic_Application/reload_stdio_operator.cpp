#include <iostream>
using namespace std;

/*重载运算符<<和>>，重载的函数不能为类的成员函数*/
class P
{
public:
    int x,y;

    P()
    {
        x=0;y=0;
    }

    P(int z,int b)
    {
        x=z;y=b;
    }
    friend ostream &operator <<(ostream &out,P p);  //声明为友元函数
    friend istream &operator >>(istream &in,P &p);
};

ostream& operator<<(ostream &out,P p)
{
    out<<"x:"<<p.x<<",y:"<<p.y<<endl;
    return out;
}

istream& operator >>(istream &in,P &p)
{
    cout<<"请输入x和y的值："<<endl;
    in>>p.x;
    in>>p.y;
    cout<<"您输入的是 "<<p.x<<endl;
    return in;
}


int main( )
{
    P p;
    cin>>p;
    cout<<p;
    return 0;
}

