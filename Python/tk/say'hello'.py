import tkinter as tk

class App:
    def __init__(self,master):
        frame = tk.Frame(master)
        frame.pack(side = tk.LEFT,padx=20,pady=20)

        self.hi_there = tk.Button(frame,text = 'hello',bg = 'grey',fg='green',command = self.say_hi)
        self.hi_there.pack()

    def say_hi(self):
        print('hello everybody ,what\'re you guys doing')

root= tk.Tk()
app = App(root)

root.mainloop()
