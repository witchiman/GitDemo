from tkinter import*

root = Tk()

text= Text(root,width= 30,height = 20)
text.pack()
text.insert(INSERT,'hello everybody') #光标位置插入

photo = PhotoImage(file = '2.gif')


def show():
    print('oh,insert a picture')
    text.image_create(END,image=photo)#插入图片 
b1= Button(text,text='dot me',command = show) 
text.window_create(INSERT,window=b1)

mainloop()
