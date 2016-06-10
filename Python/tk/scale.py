from tkinter import *

root = Tk()

s1 = Scale(root,from_=0,to=42,tickinterval =5,resolution=5)
s1.pack()
s2= Scale(root,from_=0,to=100,orient = HORIZONTAL,tickinterval =20,resolution=10)
s2.pack()

def show():
    print(s1.get(),s2.get())
Button(root,text = '获取位置',command= show).pack()

mainloop()
