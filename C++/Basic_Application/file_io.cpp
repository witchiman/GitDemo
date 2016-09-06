#include <iostream>
#include <fstream>
#include <cstring>
using namespace std;

void openfile();
void createfile();
void test_read_write();

int main( )
{
    //打开文件
    //open_file();

    //创建文件
    //create_file();

    //使用read()和write()
    //test_read_write();



    return 0;
}

void open_file()
{
    fstream file;
    file.open("test.txt");
    if(file.fail())
    {
        cout<<"打开文件失败"<<endl;
    }

    char str[50];
    while(file>>str)
    {
        cout<<str<<"|";
    }
    file<<"END"<<endl;
    file.close();
}

void create_file()
{
    ofstream ou("mydata.txt");
    ifstream in;
    ou<<"hello xiao kitty"<<endl;
    in.open("mydata.txt");
    if(in.fail())          //或if(!in){}
    {
        cout<<"打开文件失败"<<endl;
    }

    char str[30];
    while(in>>str)
    {
        cout<<str<<endl;  //每次只能读入一组字符，以空格为界
    }
    int i=0;
    while(in)
    {
        in>>str[i];       //读取所有字符，不包括空格
        i++;
    }

    cout<<str<<endl;
    in.close();
    ou.close();
}

void test_read_write()
{
    ofstream ofile("test2.txt");
    ifstream ifile;
    int n=12;
    char str[]="我是中国人";
    while(!ofile)
    {
        cout<<"输出失败！"<<endl;
    }
    ofile.write((char*)&n, sizeof(int));
    ofile.write(str, strlen(str));
    ofile.close();

    ifile.open("test2.txt");
    if(!ifile)
    {
        cout<<"打开文件失败！"<<endl;
    }
    int m;
    char str2[50];
    ifile.read((char*)&m, sizeof(int));
    ifile.read(str2, strlen(str));
    cout<<"m is "<<m<<",str2 is "<<str2<<endl;
}
