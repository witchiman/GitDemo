from tkinter import*

root=Tk()

#wrap 使之可以循环
w = Spinbox(root,values=('鸣人','佐助','斑'),from_=0,to = 10,wrap=Truew.pack())


mainloop()
