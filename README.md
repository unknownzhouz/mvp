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

- 基础V层，因为简化设计框架，不在持有P（P已经在`AndBaseActivity`进行绑定）；
- 提供的是空实现

####  `BasePresenter`

- `BasePresenterImpl`  层持有View（弱引用），防止内存泄露；
- 提供 V 层的 `LifecycleProvider`（Rx网络请求需要）



### 网络框架建设

- 使用组件 `retrofit2`、`okhttp3`、`RxAndroid` 、`com.trello.rxlifecycle3 ` ；
- 网络请求层请求绑定监听Activity生命周期，避免内存泄露；
- 网络IO线程`Schedulers.io()` 和 UI主线程 `AndroidSchedulers.mainThread()` 切换；
- 网络异常全局拦截机制；
- 数据请求和响应数据绑定；
- Token机制**没有加入**这次的网络框架，



### 流程案例

- #### 定义接口 V 继承 `BaseView` 

  ```kotlin
  interface MainView : BaseView {
      fun onLoadingDialog(show: Boolean) // 进度流程
      fun onSuccess(errorCode: Int, message: String) // 注销成功
      fun onFailure(errorCode: Int, message: String) // 注销失败
  }
  ```

- #### 定义对象 P 继承 `BasePresenterImpl` 

  ```kotlin
  // P层持有V弱引用, MainView
  class MainPresenter : BasePresenterImpl<MainView>() {
      fun requestLogout(): Boolean {
          view?.onLoadingDialog(true)
          Repository.requestLogout(lifecycleProvider, object : ResponseImpl<Any>() {
              override fun onSuccess(errorCode: Int, errorMsg: String, data: Any?) {
                  view?.onSuccess(errorCode, errorMsg)
                  view?.onLoadingDialog(false)
              }
  
              override fun onFailure(throwable: Throwable?, errorCode: Int, errorMsg: String) {
                  view?.onFailure(errorCode, errorMsg)
                  view?.onLoadingDialog(false)
              }
          })
          return true
      }
  }
  ```

  

- #### 定义`Activity` 继承 `BaseActivity<V, P>`

  ```kotlin
  // 根据当前泛型类型，顶层基类创建Presenter实例并和View进行绑定
  class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {
      fun request(){
          presenter.requestLogout()
      }
      
      override fun onLoadingDialog(show: Boolean) {
          if (show) {
              showDialog("注销账号...")
          } else {
              dismissDialog()
          }
      }
      
      override fun onSuccess(errorCode: Int, message: String) {
          Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG).show()
      }
  
      override fun onFailure(errorCode: Int, message: String) {
          Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG).show()
      }
  }
  ```

  



