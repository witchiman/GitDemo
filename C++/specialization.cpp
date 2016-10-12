#include <iostream>
using namespace std;

template<class T, class U>
class S
{
public:
    void f()
    {
        cout<<"主模板"<<endl;
    }
};

template<class T>
class S<T, int>   //第二个参数为int
{
public:
    void f()
    {
        cout<<"U==int"<<endl;
    }
};

template<class U>
class S<double, U> //第一个参数为double
{
public:
     void f()
    {
        cout<<"T==double"<<endl;
    }
};

template<class T>
class S<T, T>   //T==U
{
public:
     void f()
    {
        cout<<"T==U"<<endl;
    }
};

template<class T, class U>  //两个参数都为指针
class S<T*, U*>
{
public:
     void f()
    {
        cout<<"T==*T, U==*U"<<endl;
    }
};

int main( )
{
    S<int, float> s1;
    s1.f();

    S<double,float> s2;
    s2.f();

    S<float,float> s3;
    s3.f();

    S<int*, double*> s4;
    s4.f();

    S<int,int> s5;  //模板相近，编译器不能区分，导致编译错误
    s5.f();

    return 0;
}
