/**
 * 房屋管理系统管理初始化
 */
var HouseManager = {
    id: "HouseManagerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
HouseManager.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '编号', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '业主姓名', field: 'houseUser', visible: true, align: 'center', valign: 'middle'},
        {title: '房屋面积', field: 'houseArea', visible: true, align: 'center', valign: 'middle'},
        {title: '房屋地址', field: 'houseAddress', visible: true, align: 'center', valign: 'middle'},
        {title: '交付日期', field: 'houseTime', visible: true, align: 'center', valign: 'middle'},
        {title: '有无贷款', field: 'houseLoan', visible: true, align: 'center', valign: 'middle'},
        {title: '房屋描述', field: 'houseDesc', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
HouseManager.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        HouseManager.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加房屋管理系统
 */
HouseManager.openAddHouseManager = function () {
    var index = layer.open({
        type: 2,
        title: '添加房屋管理系统',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/houseManager/houseManager_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看房屋管理系统详情
 */
HouseManager.openHouseManagerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '房屋管理系统详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/houseManager/houseManager_update/' + HouseManager.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除房屋管理系统
 */
HouseManager.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/houseManager/delete", function (data) {
            Feng.success("删除成功!");
            HouseManager.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("houseManagerId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询房屋管理系统列表
 */
HouseManager.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    HouseManager.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = HouseManager.initColumn();
    var table = new BSTable(HouseManager.id, "/houseManager/list", defaultColunms);
    table.setPaginationType("client");
    HouseManager.table = table.init();
});
