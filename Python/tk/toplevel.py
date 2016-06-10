from tkinter import*
root = Tk()

def create():
    top = Toplevel()
    top.attributes('-alpha',0.5) #透明度
    top.title('it')
    msg = Message(top,text='I love the world!')
    msg.pack()
Button(root,text='创建顶级窗口',command=create).pack()


mainloop()
