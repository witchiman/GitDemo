#include <iostream>
#include "graphics.h"
#include <ege.h>
using namespace std;

/*由于WIN10系统对VC6的兼容性不好，故选择CodeBlocks作为IED，因此无法使用EasyX,
选择了与其用法相近的图形库EGE。

*/
const int WIDTH = 1000;
const int HEIGHT = 600;
class Figure
{
    protected:
        int dx = 1;
        color_t c;
    public:
        virtual void draw() = 0;
        void speedUp()
        {
            dx += 1;
        }
        void speedDown()
        {
            if(dx > 1)
             {
                dx -= 1;
             }
        }
        virtual void update() = 0;
        int getDx()
        {
            return dx;
        }
};
class Circle : public Figure
{
    private:
        int x, y ,r;
    public:
       Circle(int cx, int cy, int radius, color_t color)
       {
            x = cx;
            y = cy;
            r = radius;
            c = color;
       }
        Circle(int cx, int cy, int radius)
       {
            x = cx;
            y = cy;
            r = radius;
            c = WHITE;
       }
        void draw()
        {
            setcolor(c);
            circle(x , y , r);
        }

        void update()
        {
            x += dx;
        }
};

class Rect : public Figure
{
    private:
        int left, top, right, bottom;
    public:
        Rect(int l, int t, int r, int b)
        {
            left = l;
            top = t;
            right = r;
            bottom = b;
            c = WHITE;
        }
        void draw()
        {
            setcolor(c);
            rectangle(left, top, right, bottom);
        }
        void update()
        {
            left += dx;
            right += dx;
        }
};

class Triangle : public Figure
{
    private:
        int x1, y1, x2, y2, x3, y3;
    public:
        Triangle(int xx1, int yy1, int xx2, int yy2, int xx3, int yy3)
        {
            x1 = xx1;
            y1 = yy1;
            x2 = xx2;
            y2 = yy2;
            x3 = xx3;
            y3 = yy3;
            c = WHITE;
        }
        void draw()
        {
            setcolor(c);
            line(x1, y1, x2, y2);
            line(x1, y1, x3, y3);
            line(x2, y2, x3, y3);
        }
        void update()
        {
            setcolor(c);
            x1 += dx;
            x2 += dx;
            x3 += dx;
        }
};
class Vehicle
{
    protected:
        int num, i, head;  //head 标记车头的位置
        Figure *contents[7];
    public:
        void draw()
        {
            for(i=0; i<num; i++)
            {
                contents[i]->draw();
            }
        }
        void run()
        {
            for(i=0; i<num; i++)
           {
               if(head < WIDTH - 20)
                    contents[i]->update();
            }
            head += contents[1]->getDx() ;  //车头位置不断向前移动
        }
        void speedUp()
        {
            for(i=0; i<num; i++)
            {
                contents[i]->speedUp();
            }
        }
        void speedDown()
        {
            for(i=0; i<num; i++)
            {
                contents[i]->speedDown();
            }
        }
};
class Car : public Vehicle
{
    public:
        Car()
        {
            num = 6;
            head = 320;
            contents[0] = new Rect(30, 320, 320, 360);
            contents[1] = new Circle(100, 380, 20);
            contents[2] = new Circle(250, 380, 20);
            contents[3] = new Triangle(80, 320, 120, 280, 120, 320);
            contents[4] = new Triangle(230, 280, 230, 320, 270, 320);
            contents[5] = new Rect(120, 280, 230, 320);
         }
};

class Truck : public Vehicle
{
    public:
        Truck()
        {
            num = 7;
            head = 472;
            contents[0] = new Rect(30, 200, 390, 360);
            contents[1] = new Rect(392, 280, 472, 360);
            contents[2] = new Circle(70, 380, 20);
            contents[3] = new Circle(350, 380, 20);
            contents[4] = new Circle(120, 380, 20);
            contents[5] = new Circle(300, 380, 20);
            contents[6] = new Circle(432, 380, 20);
        }
};

void init()  //场景初始化函数
{
    setcolor(WHITE); //设定绘图颜色
    outtextxy(0, 5,"1Car    2Truck    3Exit");
    outtextxy(35, 35, "Press <S> key to start moving ");
    outtextxy(35, 55, "Press <P> key to pause/continue moving");
    outtextxy(35, 75, "Press <E> key to end moving");
    outtextxy(35, 95, "Press <+> key to speed up");
    outtextxy(35, 115, "Press <-> key to speed down");
    outtextxy(35, 135, "    注:须开启大写模式，否则可能造成程序未响应!");
    rectangle(30,30,980,580);
    setfillcolor(WHITE);
    bar(30, 400, 980, 405); //画粗实线

}

/*新建一个场景类，初始化场景内的
各个物体
*/
class Scene
{
    private:
       Vehicle* vehicle;
       int pause, endScene;
    public:
        Scene(Vehicle *v)
        {
          pause = 0;
          vehicle = v;
        }
        void render()
        {
           init();
           vehicle->draw();
        }
        int update()
        {
            if(pause == 0)
           {
               vehicle->run();
           }
        }
        void onKey(int key)
        {

            if (key == 'P' || key == 'p') //按下P键就在播放与暂停之间转换
            {
              pause = !pause;
            }

            if(key == '+')
            {
                vehicle->speedUp();
            }
            if(key == '-')
            {
                vehicle->speedDown();
            }

        }
        ~Scene()
        {
            delete vehicle;
        }

};

 void mainloop()
{
    init();
    bool generator = false;
    bool running = false; //running为1表示动画运行
    bool exit = false;
    Scene *s;     //定义场景指针

   for ( ; ege::is_run(); ege::delay_fps(60) )
	{
       while(kbhit())
       {
           int key = getch();
           switch (key)
           {
            case '1':
                s = new Scene(new Car());
                generator = true;
                break;
            case '2':
                s = new Scene(new Truck());
                generator = true;
                break;
            case '3':
                exit = true;
            case 'S':
                 running = true;
                 break;
            case 'E':
                 generator = false;
                 ege::cleardevice();
				 init();
                 break;
            default:
                 s->onKey(key);
           }
       }

       if(exit)
          break;

     if(generator)
       {
        if(running)
        {
            s->update();
        }
        ege::cleardevice();
        s->render();
       }
    }

}

int main()
{
    initgraph(WIDTH,HEIGHT);
    mainloop();
    getch();
    closegraph();
    return 0;
}
