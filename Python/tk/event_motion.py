from tkinter import*

root=Tk()

def callback(event):
    print('鼠标：',event.x,event.y)
    


frame=Frame(root,width=200,height=200)
frame.bind('<Motion>',callback)
frame.focus_set()
frame.pack()

mainloop()
