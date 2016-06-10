from tkinter import*
root = Tk()

def callback():
    print('Hello!')

mb = Menubutton(root,text='dotme',relief=RAISED)
mb.pack()

filemenu = Menu(mb,tearoff=True)
filemenu.add_command(label='open',command=callback)
filemenu.add_command(label='save',command=callback)
filemenu.add_separator()
filemenu.add_command(label='quit',command=root.quit)


mb.config(menu=filemenu)
mainloop()

