from tkinter import*

OPTIONS = ['California','2424','fadf','LaFerrari','Tom$$Tim']

root = Tk()

variable=StringVar()
variable.set(OPTIONS[0])
w = OptionMenu(root,variable,*OPTIONS)
w.pack()



mainloop()

