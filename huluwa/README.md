# 《葫芦娃大战妖精》说明书
致各位看官：
 我将分**游戏说明**和**实现说明**两部分进行解释，如果闲话太多，各位自取相关部分浏览。😀😀😀
 #  游戏说明
 ***
## 游戏背景
由于妖精为祸四方，作恶多端，葫芦娃与爷爷作为正义的化身，对抗由蛇精和蝎子精带领的六个蜈蚣精，由于葫芦娃团结一致和两个妖精诡计多端，双方僵持不下，变换各种阵型进行对抗，在葫芦山下斗得天昏地暗。考虑到双方体力有限，只能进行20局对抗（实际是我就写了20个存档😰），欢迎各方看官来围观。
## 游戏内容
### 战斗成员
- 葫芦娃：体力100；杀伤力25；步速8
- 爷爷：体力30；杀伤力5；步速3
- 蛇精和蝎子精：体力100；杀伤力45；步速12
- 小喽啰：体力65；杀伤力18；步速6
### 战斗过程
- 双方以随机的阵型列于战场两侧；
- 开始战斗之后，双方以小于步速最大值的随机步伐前进，寻找最近的敌人进行攻击，以小于最大杀伤力的战斗力减少敌人体力；
- 当前敌人阵亡后，寻找下一个可攻击对象，直到一方全部成员阵亡，有存活的一队胜利；战斗过程自动存入文件中；
- 选择文件进行回放，需要看官按**N**推进回放进程（一直按着N就像快进一样😝可以很快回放完）；
## 游戏操作
####  快捷键操作
- **空格**  开始游戏
- **R** 在游戏尚未开始时变换阵型
- **P** 暂停游戏
- **C** 继续游戏
- **L** 游戏读档
- **N** 推进读档
####  菜单操作
- 游戏：开始游戏，读档回放，规则说明，退出游戏；
- 选项：变换阵型，暂停，继续；
# 实现说明
***
##  结构
### 主体结构
#### base(基础类)
- Position：作为整个游戏的基础，每个坐标可以站一个生物，通过**泛型**实现；
#### Creature（生物）
- Creature：抽象类，作为各个子类的基础，定义的生物的基本属性，包括每个生物的移动、体力、战斗、状态判断（比较重要的代码在类的前部并标有注解）；
-	Calabash：子类，生物特性部分的定义和加载，如体力、杀伤力、生前照片及遗照；生成移动终点；
-	Grandpa、Magnate、Matrix子类同上；
#### Formation（阵型）
- FormationFac:采用**工厂模式**作为阵型的生成器；
- Formation：抽象类，定义各个阵型的基本信息；
- 八种类型的阵形；
#### ioFile（读档存档）
- WriteFile：存档，随机生成文档名称，在文档的末尾进行当前信息的添加，在生物有动作的时候进行记录，即初始化、移动、体力下降、被打死的时候录入信息；
- ReadFile：读档，弹出文件选择框，读取生物基本信息进行初始化，之后随着用户按下**N**逐行读取文档内容，直到文档末尾；
#### ui（界面生成及游戏状态切换）
- Ground:创建窗口的类；
- Menu：为窗口添加菜单及菜单响应，此处使用了**观察者模式**及**匿名方法**（本来想用lambda表达式，但是我的java版本低不支持😭）；
- Field:继承JPanel类，定义了游戏所需要的生物及读存档器；射击到游戏的主逻辑：开始、结束、暂停、继续、刷新阵型、游戏操作详解；定义键盘响应，此处也用了**观察者模式**（虽然是拿来主义，嘻嘻）（比较重要的代码在类的前部并标有注解）；
### 测试结构
- 对**最近敌人方法**及**读档方法**进行测试，结果无误；
## 应用内容
1.	用**泛型**实现position类，同时用<? extend Creature>处理边界；用collection重新定义了相关数组；
2.	定义**抽象类**、**抽象方法**及继承子类；
3.  采用**工厂模式**实现阵型生成；
4.	采用**观察者模式**实现窗口菜单响应及键盘响应；
5.  在实现多线程的操作时使用了**synchronized**关键字，防止发生死锁现象；
6.  在进行存档和读档过程中进行io异常的捕捉；
7.  将读档器、存档器独立，减少了类之间的关联；