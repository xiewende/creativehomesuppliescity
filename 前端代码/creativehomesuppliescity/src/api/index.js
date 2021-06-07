import ajax from './ajax'

/**
 * 品牌管理
 */
// 删除某一个品牌
export const reqBrandDelete = (id) => ajax('/brand/deleteBrand', { id })

// 添加品牌
export const reqBrandAdd = (brandName, description, designerName) => ajax('/brand/insertBrand', { brandName, description, designerName })

// 查询全部品牌（不带分页，供其他选择品牌使用）
export const reqBrandList = () => ajax('/brand/listAllBrand')

// 查询全部品牌（分页，供展示的时候）
export const reqBrandListByPage = (pageNum) => ajax('/brand/listAllBrandByPage', { pageNum })

// 根据id查询某品牌
export const reqBrandSelectById = (id) => ajax('/brand/selectBrandById', { id })

// 修改品牌信息
export const reqBrandUpdate = (brandName, description, designerName, id) => ajax('/brand/updateBrand', { brandName, description, designerName, id })


/**
 * 商品一级分类管理
 */
// 删除某一个一级分类
export const reqCategoryFirstDelete = (id) => ajax('/categoryFirst/deleteCategoryFirst', { id })

// 根据id查询某一级分类
export const reqCategoryFirstFindById = (id) => ajax('/categoryFirst/findCategoryFirstById', { id })

// 添加一级分类
export const reqCategoryFirstAdd = (categoryName) => ajax('/categoryFirst/insertCategoryFirst', { categoryName })

// 查询全部一级分类
export const reqCategoryFirstList = () => ajax('/categoryFirst/listAllCategoryFirst')

// 修改一级分类信息
export const reqCategoryFirstUpdate = (id, categoryName) => ajax('/categoryFirst/updateCategoryFirst', { id, categoryName })


/**
 * 商品二级分类管理
 */
// 删除某一个二级分类
export const reqCategorySecondDelete = (id) => ajax('/categorySecond/deleteCategorySecond', { id })

// 根据id查询某二级分类
export const reqCategorySecondFindById = (id) => ajax('/categorySecond/findCategorySecondById', { id })

// 添加二级分类
export const reqCategorySecondAdd = (categoryFirstName, categorySecondName) => ajax('/categorySecond/insertCategorySecond', { categoryFirstName, categorySecondName })

// 查询全部二级分类
export const reqCategorySecondList = (firstId) => ajax('/categorySecond/listAllCategorySecond', { firstId })

// 修改二级分类信息
export const reqCategorySecondUpdate = (categoryFirstName, categorySecondName, id) => ajax('/categorySecond/updateCategorySecond', { categoryFirstName, categorySecondName, id })


/**
 * 设计师管理
 */
// 删除某一个设计师
export const reqDesignerDelete = (id) => ajax('/designer/deleteDesigner', { id })

// 添加设计师
export const reqDesignerAdd = (college, designConcept, designerName, email, file, iphone, professionalTitle, sex, time, type) => ajax('/designer/InsertDesigner', { college, designConcept, designerName, email, file, iphone, professionalTitle, sex, time, type })

// 条件查询全部设计师（不带分页，供其他选择设计师使用）
export const reqDesignerList = (professionalTitle, type) => ajax('/designer/listDesigner', { professionalTitle, type })

// 条件查询全部设计师（带分页，查询全部展示使用）
export const reqDesignerListByPage = (pageNum, professionalTitle, type) => ajax('/designer/listDesignerByPage', { pageNum, professionalTitle, type })

// 根据id查询某设计师
export const reqDesignerSelectById = (id) => ajax('/designer/selectDesignerById', { id })

// 查询设计师某一位设计师的详情
export const reqDesignerDetail = (id) => ajax('/designer/selectDesignerDeatils', { id })

// 修改设计师
export const reqDesignerUpdate = (college, designConcept, designerName, email, file, id, iphone, professionalTitle, sex, time, type) => ajax('/designer/updateDesigner', { college, designConcept, designerName, email, file, id, iphone, professionalTitle, sex, time, type }, )

// 修改密码
export const reqDesignerPasswordUpdate = (id, newPassword, oldPassword) => ajax('/designer/updateDesignerPassword', { id, newPassword, oldPassword })


/**
 * 家居用品管理
 */
// 删除某一个家居用品
export const reqGoodsDelete = (id) => ajax('/goods/deleteGoods', { id })

// 添加家居用品
export const reqGoodsAdd = (brandName, categorySecondName, description, file, goodName, price, size, store, styleName) => ajax('/goods/insertGoods', { brandName, categorySecondName, description, file, goodName, price, size, store, styleName })

// 查询全部家居用品，全部输入参数都是可选参数可以为空
export const reqGoodsListWithSomeDemand = (brandName, categoryFirstName, categorySecondName, goodName, pageNum, stypeName) => ajax('/goods/listGoodsWithSomeDemand', { brandName, categoryFirstName, categorySecondName, goodName, pageNum, stypeName })

// 根据id查询某家居用品详情
export const reqGoodsDetail = (id) => ajax('/goods/selectGoodsDetails', { id })

// 修改家居用品信息
export const reqGoodsUpdate = (brandName, categorySecondName, description, file, goodName, id, price, size, store, styleName) => ajax('/goods/updateGoods', { brandName, categorySecondName, description, file, goodName, id, price, size, store, styleName })


/**
 * 订单管理
 */
// 管理端确认订单,进而进行发货
export const reqOrderConfirmShipments = (id, logisticsName) => ajax('/order/confirmShipments', { id, logisticsName })

// 管理员查询全部订单
export const reqOrderSelectWithDemand = (pageNum, statusName, userName) => ajax('/order/selectOrderWithDemand', { pageNum, statusName, userName })

// 管理端查询新的订单
export const reqOrderSelectNew = (pageNum) => ajax('/order/selectNewOrder', { pageNum })

// 用户确认收货
export const reqOrderConfirmReceiveGoods = (id) => ajax('/order/confitmReceiveGoods', { id })

// 选择购物车的时候计算总价
export const reqOrderCountTotalPrice = (idList) => ajax('/order/countTotalPrice', { idList })

// 若完成后可以选择删除某一个订单
export const reqOrderDelete = (id) => ajax('/order/deleteOrder', { id })

// 下单操作
export const reqOrderAdd = (address, idList, iphone, payTyle, receiveName, userId) => ajax('/order/insertOrder', { address, idList, iphone, payTyle, receiveName, userId })

// 用户查询自己的订单
export const reqOrderUserSelect = (userId) => ajax('/order/selectUserOrder', { userId })

// 查询某一个订单的详情
export const reqOrderDetail = (id) => ajax('/order/selectOrderDetail', { id })


/**
 * 购物车管理接口
 */
// 删除某一个购物车项
export const reqOrderCartDelete = (id) => ajax('/orderCart/deleteOrderCart', { id })

// 添加购物车
export const reqOrderCartAdd = (goodsId, num, userId) => ajax('/orderCart/insertOrderCart', { goodsId, num, userId })

// 查询全部购物车
export const reqOrderCartList = (pageNum, userId) => ajax('/orderCart/listAllOrderCartByUser', { pageNum, userId })

// 修改购物车某一项的商品个数
export const reqOrderCartNumUpdate = (id, newNum) => ajax('/orderCart/updateOrderCartNum', { id, newNum })


/**
 * 风格管理
 */
// 删除某一个风格
export const reqStyleDelete = (id) => ajax('/style/deleteStyle', { id })

// 添加风格
export const reqStyleAdd = (description, typeName) => ajax('/style/insertStyle', { description, typeName })

// 查询全部风格（不带分页，供其他选择风格使用）
export const reqStyleList = () => ajax('/style/listAllStyle')

// 查询全部风格（分页，供展示的时候）
export const reqStyleListByPage = (pageNum) => ajax('/style/listAllStyleByPage', { pageNum })

// 修改风格信息
export const reqStyleUpdate = (description, id, typeName) => ajax('/style/updateStyle', { description, id, typeName })

// 根据id查询某风格
export const reqStyleSelectById = (id) => ajax('/style/selectStyleById', { id })


/**
 * 用户信息
 */
// 删除某一个用户
export const reqUserDelete = (id) => ajax('/user/deleteUser', { id })

// 按照名字模糊查询用户，若没有输入则查询全部
export const reqUserListOrName = (pageNum, userName) => ajax('/user/listUserOrName', { pageNum, userName })

// 根据id查询某一位用户
export const reqUserSelectById = (id) => ajax('/user/selectUserById', { id })

// 修改密码
export const reqUserPasswordUpdate = (id, newPassword, oldPassword) => ajax('/user/updatePassword', { id, newPassword, oldPassword })

// 修改用户信息
export const reqUserUpdate = (id, address, email, file, iphone, password, sex, userName) => ajax('/user/updateUserOther', { id, address, email, file, iphone, password, sex, userName })


/**
 * 个人定制管理
 */
// 管理员查询全部的设计方案，选择条件模糊查询
export const reqSelectAllProgram = (designerName, goodsName, pageNum, userName) => ajax('/program/adminSelectAllProgram', { designerName, goodsName, pageNum, userName })

// 若完成后可以选择删除定制内容
export const reqProgramDelete = (id) => ajax('/program/delectProgram', { id })

// 设计师完成设计,提交设计图等
export const reqProgramFinish = (file, finishIdea, id) => ajax('/program/finishThisProgram', { file, finishIdea, id }, )

// 添加个人定制
export const reqProgramAdd = (designerId, goodName, otherDemand, size, userId) => ajax('/program/insertProgram', { designerId, goodName, otherDemand, size, userId })

// 用户接受这个定制
export const reqProgramReceive = (id) => ajax('/program/receiveThisProgram', { id })

// 用户拒绝这个定制
export const reqProgramReject = (id) => ajax('/program/rejectThisProgram', { id })

// 设计师查看向自己的定制内容
export const reqProgramSelectByDesignerId = (designerId, isNew) => ajax('/program/selectProgramByDesignerId', { designerId, isNew })

// 用户查看自己的定制
export const reqProgramSelectByUserId = (userId) => ajax('/program/selectProgramByUserId', { userId })


/**
 * 成功案例
 */
// 若完成后可以选择删除一个定制内容
export const reqFinnishProgramDelete = (id) => ajax('/finnishProgram/deleteFinnishProgram', { id })

// 管理员查看全部成功案例（两个名字模糊）
export const reqFinnishProgram = (designerName, pageNum, userName) => ajax('/finnishProgram/listAllFinnishProgramWithSomeDemand', { designerName, pageNum, userName })


/**
 * 用户注册登录
 */
// 登录
export const reqLogin = (name, password, tag) => ajax('/login', { name, password, tag })

// 退出登录
export const reqLoginOut = () => ajax('/loginout', {})

// 用户注册
export const reqRegister = (address, affirmPassword, email, file, iphone, password, sex, userName) => ajax('/register', { address, affirmPassword, email, file, iphone, password, sex, userName })