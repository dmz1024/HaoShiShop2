package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.bean.zongqinghui.SubGroupBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.ContextUtil;
import util.RxBus;
import view.pop.TipMessage;

/**
 * Created by dengmingzhi on 2017/3/20.
 */

public class FriendAndFlockController {
    public static FriendAndFlockController getInstance() {
        return new FriendAndFlockController();
    }

    public void addFlock(final String gid, final String note, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("groupid", gid);
                map.put("note", note);
                return map;
            }

            @Override
            protected String getMsg(int code) {
                if (code == 10002) {
                    return "已添加过该群";
                }
                return super.getMsg(code);
            }

            @Override
            protected String getUrl() {
                return ApiConstant.JOIN_GROUP;
            }


            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }

        }.setOnRequestListeren(listener).post(new TipLoadingBean("申请加入", "申请成功", "申请失败"));
    }

    public void addFriend(final String fid, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("fid", fid);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.JOIN_FRIEND;
            }

            @Override
            protected String getMsg(int code) {
                if (code == 10002) {
                    return "已添加过该好友";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }

        }.setOnRequestListeren(listener).post(new TipLoadingBean("添加好友", "添加成功", ""));
    }

    /**
     * 修改分组名称
     *
     * @param id
     * @param name
     * @param listener
     */
    public void updateGroupName(final String id, final String name, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("groupid", id);
                map.put("name", name);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.EDIT_SUBGROUP;
            }

            @Override
            protected String getMsg(int code) {
                if (code == 10001) {
                    return "名称不能为空";
                }
                switch (code) {
                    case 10001:
                        return "名称不能为空";
                    case 10002:
                        return "分组不存在";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }

        }.setOnRequestListeren(listener).post(new TipLoadingBean("修改分组名称", "修改成功", "修改失败"));
    }

    /**
     * 删除分组
     * @param id
     * @param listener
     */
    public void deleteGroup(final String id, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("groupid", id);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.DEL_SUBGROUP;
            }

            @Override
            protected String getMsg(int code) {
                if (code == 10001) {
                    return "分组不存在";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }

        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在删除分组", "删除成功", "删除失败"));
    }


    /**
     * 添加分组
     * @param name
     */
    public void addGroup(final String name) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("name", name);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADD_SUBGROUP;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                RxBus.get().post("groupNotifyDataRxBus", "");
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean("正在添加分组", "分组添加成功", ""));
    }


    /**
     * 删除好友
     *
     * @param fid
     * @param listener
     */
    public void deleteFriend(final String fid, final OnSingleRequestListener<SingleBaseBean> listener) {
        new TipMessage(ContextUtil.getCtx(), new TipMessage.TipMessageBean("提示", "是否确认删除好友?", "取消", "删除")) {
            @Override
            protected void right() {
                super.right();
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("fid", fid);
                        return map;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.OUT_FRIEND;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(listener).post(new TipLoadingBean("正在删除好友", "删除成功", "删除好友失败"));
            }
        }
                .showAtLocation(false);

    }


    /**
     * 获取分组列表
     * @param listener
     */
    public void getGroupList(final OnSingleRequestListener<SubGroupBean> listener){
        new ApiRequest<SubGroupBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String,String> map=new HashMap<>();
                map.put("uid",UserInfo.userId);
                map.put("token",UserInfo.token);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.SUBGROUP_LIST;
            }

            @Override
            protected Class<SubGroupBean> getClx() {
                return SubGroupBean.class;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("获取分组列表","","获取失败"));
    }


    /**
     * 编辑亲人备注
     * @param listener
     * @param content
     */
    public void editSubGroup(OnSingleRequestListener<SingleBaseBean> listener,final String... content){
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("fid", content[0]);
                map.put("groupid", content[1]);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.EDIT_FRIEND;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在修改好友分组", "修改成功", ""));
    }
    /**
     * 编辑亲人分组
     * @param listener
     * @param content
     */
    public void editNote(OnSingleRequestListener<SingleBaseBean> listener,final String... content){
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("fid", content[0]);
                map.put("note", content[1]);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.EDIT_FRIEND;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在修改好友备注", "修改成功", ""));
    }

}
