# M-VP设计模式

更准确的说是~~M~~-VP设计模式，如果没有涉及到SQLite使用，基本都是网络接口，Model在这里基本是简化了；

### 基类建设

#### `AndBaseActivity `、`AndBaseFragment` 顶层基类；

- 监听生命周期 `com.trello.rxlifecycle3`，对网络请求进行Rx事件绑定，防止内存泄露；
- 建立 `Presenter` 层，使用泛型进行 `<V, P>` 绑定；

#### `BaseActivit`、`BaseFragment` 业务基类；

- 提供基础弹出框、输入框软键盘拦截等等
- 全局业务处理等

#### `BaseModel` 

- 基础M层，因为简化设计框架，就不再使用；

#### `BaseView`

- 基础V层，因为简化设计框架，不在持有P（P已经在AndBaseActivity进行绑定）；
- 提供的是空实现

####  `BasePresenter`

- `BasePresenterImpl`  层持有View（弱引用），防止内存泄露；
- 提供 V 层的 `LifecycleProvider`（Rx网络请求需要）



### 流程案例

- #### 定义接口 V 继承 `BaseView` 

  ```kotlin
  interface MainView : BaseView {
      fun onSuccess(code: String, message: String)  // 获取数据成功
      fun onFailure(code: String, message: String)  // 获取数据失败
  }
  ```

- #### 定义对象 P 继承 `BasePresenterImpl` 

  ```kotlin
  // P层持有V弱引用, MainView
  class MainPresenter : BasePresenterImpl<MainView>() {
       fun requestTest() {
           // Todo 请求数据异步线程
           Thread {
              try {
                  Thread.sleep(3000)
                  view?.onSuccess("0", " 数据请求成功 ")
              } catch (e: InterruptedException) {
                  view?.onFailure("2000", "数据异常")
              }
          }.start()
       }
  }
  ```

  

- #### 定义`Activity` 继承 `BaseActivity<V, P>`

  ```kotlin
  // 根据当前泛型类型，顶层基类创建Presenter实例并和View进行绑定
  class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {
      fun request(){
          presenter.requestTest()
      }
      
      override fun onSuccess(code: String, message: String) {
         dismissDialog()
         Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG).show()
      }
  
      override fun onFailure(code: String, message: String) {
         dismissDialog()
         Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG).show()
      }
  }
  ```

  



