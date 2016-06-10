from tkinter import*
root = Tk()

def callback():
    print('Hello!')
menubar = Menu(root)

openVar = IntVar()
saveVar = IntVar()
quitVar = IntVar()

filemenu = Menu(menubar)
filemenu.add_checkbutton(label='open',command=callback,variable=openVar)
filemenu.add_checkbutton(label='save',command=callback,variable=saveVar)
filemenu.add_separator()
filemenu.add_checkbutton(label='quit',command=root.quit,variable=quitVar)
menubar.add_cascade(label='file',menu=filemenu)

editVar = IntVar()

editmenu = Menu(menubar,tearoff=False)
editmenu.add_command(label='out',command=callback)
editmenu.add_command(label='copy',command=callback)
editmenu.add_command(label='paste',command=callback)
menubar.add_cascade(label='edit',menu=editmenu)
editmenu.add_radiobutton(label='out',command=callback,variable=editVar,value=1)
editmenu.add_radiobutton(label='copy',command=callback,variable=editVar,value=2)


root.config(menu=menubar)

mainloop()
