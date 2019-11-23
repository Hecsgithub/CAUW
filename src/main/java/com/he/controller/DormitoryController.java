package com.he.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Class;
import com.he.po.Dormitory;
import com.he.po.DormitoryAllocation;
import com.he.po.DormitoryRegistration;
import com.he.po.Major;
import com.he.po.RespBean;
import com.he.po.StudentStatus;
import com.he.service.ClassService;
import com.he.service.DormitoryAllocationService;
import com.he.service.DormitoryRegistrationService;
import com.he.service.DormitoryService;
import com.he.service.MajorService;
import com.he.service.StudentStatusService;
import com.he.service.serviceImpl.DormitoryAllocationServiceImpl;
import com.he.service.serviceImpl.DormitoryRegistrationServiceImpl;
import com.he.viewpo.ClassIdAndStudentList;
import com.he.viewpo.DDMC;
import com.he.viewpo.DivideDormitoryInformation;
import com.he.viewpo.MajorAndClass;

@RequestMapping("/dormitory/alldormitory")
@RestController
public class DormitoryController {

	@Autowired
	private DormitoryService dormitoryService;

	@Autowired
	private ClassService ClassService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private DormitoryAllocationService dormitoryAllocationService;

	@Autowired
	private DormitoryRegistrationService dormitoryRegistrationService;

	/**
	 * 分宿舍给班级
	 * 
	 * @return
	 */
	@Transactional
	@RequestMapping("/dividedormitory")
	public RespBean DivideDormitory() {

//		总分男生宿舍情况
		List<DivideDormitoryInformation> boyallddis = new ArrayList<>();
//		总分女生宿舍情况
		List<DivideDormitoryInformation> girlallddis = new ArrayList<>();

//		获取每个专业每个班级未安排宿舍的男女生资料
		List<MajorAndClass> macs = this.ClassService.selectMajorAndClassList();

		if (macs.size() == 0) {
			return RespBean.error("无班级需要分配！");
		}
		// 新建DDMC对象 number属性存储要查询的宿舍的情况值（为2 查询没住人的宿舍 ，为0 未满人宿舍， 为1满人宿舍）
		// sex属性为查男女生宿舍
		DDMC d = new DDMC();
		d.setSex("男");
		d.setEnough("0");

//		男生宿舍未住人宿舍集合
		List<Dormitory> boydormitorylist = this.dormitoryService.selectAllDormitory(d);
		d.setSex("女");
//		女生宿舍未住人宿舍集合
		List<Dormitory> girldormitorylist = this.dormitoryService.selectAllDormitory(d);
		d = null;

//		更新数据库，将此班级与分配到该班级的宿舍建立联系,生成全局班级分配对象及List对象
		List<DormitoryAllocation> das = new ArrayList<>();

//		分别给每个专业男女生进行分宿舍
		for (MajorAndClass mac : macs) {
			if (mac.getClasss().size() == 0) {// 该专业未有班级
				continue;
			}
//			专业分男生宿舍情况
			List<DivideDormitoryInformation> boymajorddis = new ArrayList<>();
//			专业分女生宿舍情况
			List<DivideDormitoryInformation> girlmajorddis = new ArrayList<>();

//			获得该专业下班级详细信息
			List<ClassIdAndStudentList> cass = mac.getClasss();

			for (ClassIdAndStudentList cas : cass) {
//				获得该班级男生无床位数
				int boulessnumber = cas.getBoylessnumber();
//				获得该班级女生无床位数
				int girllessnumber = cas.getGirllessnumber();

//				分配给该班级的宿舍集合
				List<Dormitory> classdormitory = new ArrayList<>();

				int boynumber = 0;
				int girlnumber = 0;

				/**
				 * 对该班男生分班
				 */
				if (boulessnumber > 0) {

					Iterator<Dormitory> boyit = boydormitorylist.iterator();
					while (boyit.hasNext()) {

						Dormitory boyd = boyit.next();
//					   获取当前宿舍可入住人数
						boynumber += boyd.getNumber();
						/**
						 * 如可入住，则标记此宿舍删除出男生宿舍集合，并进入该班级的宿舍集合
						 */

						// 该班级恰好分配满人，不再参与分配
						if (boynumber == boulessnumber) {
							classdormitory.add(boyd);
							boyit.remove();
							break;
						} else if (boynumber < boulessnumber) {
							classdormitory.add(boyd);
//						该宿舍退出分配，不再参与
							boyit.remove();
						} else {
							// 该专业分班实情
							DivideDormitoryInformation ddi = new DivideDormitoryInformation();
							// 设置实情专业
							ddi.setMajor(mac.getMajor());
							// 设置实情班级
							ddi.setClass_id(cas.getClassId());
							// 设置实情性别
							ddi.setSex("男");

//						当前为2人宿舍,出现两人宿舍不满足前判断条件时，必为多出一个未分配，即将此2人宿舍暂时分配给这个班，
//						并将此宿舍登记到专业分宿舍情况集合中
							if (boyd.getNumber() == 2) {
								boyd.setNumber(boyd.getNumber() - 1);
								classdormitory.add(boyd);
								boyit.remove();
								// 设置实情宿舍号
								ddi.setDormitory_id(boyd.getId());
								// 设置实情空余床位
								ddi.setManynumber(1);
							} else if (boyd.getNumber() == 3) { // 3人宿舍，满足人数分别为2人时才给与宿舍
								// 若空床位大于1，则不再分配床位，与其他宿舍形成混合宿舍
								if (boynumber - (boulessnumber) > 1) {
									// 设置实情缺少床位数
									ddi.setLessnumber(boyd.getNumber() - (boynumber - (boulessnumber)));
								} else {
									boyd.setNumber(boyd.getNumber() - (boynumber - (boulessnumber)));
									classdormitory.add(boyd);
									boyit.remove();
									// 设置实情宿舍号
									ddi.setDormitory_id(boyd.getId());
									// 设置实情空余床位
									ddi.setManynumber(boynumber - (boulessnumber));
								}
							} else { // 4-5人宿舍
										// 多余的床位大于2，则不再分配床位，与其他宿舍形成混合宿舍
								if (boynumber - (boulessnumber) > 2) {
									// 设置实情缺少床位数
									ddi.setLessnumber(boyd.getNumber() - (boynumber - (boulessnumber)));
								} else {
									boyd.setNumber(boyd.getNumber() - (boynumber - (boulessnumber)));
									classdormitory.add(boyd);
									boyit.remove();
									// 设置实情宿舍号
									ddi.setDormitory_id(boyd.getId());
									// 设置实情空余床位
									ddi.setManynumber(boynumber - (boulessnumber));
								}
							}

							boymajorddis.add(ddi);
							ddi = null;

							// 班级人数已经分配完，退出
							break;
						}

					}

				}

				if (girllessnumber > 0) {

					/**
					 * 对该班女生分宿舍
					 */
					Iterator<Dormitory> it = girldormitorylist.iterator();
					while (it.hasNext()) {
						Dormitory girld = it.next();
//									   获取当前宿舍可入住人数
						girlnumber += girld.getNumber();
						/**
						 * 如可入住，则标记此宿舍删除出男生宿舍集合，并进入该班级的宿舍集合
						 */

						// 该班级恰好分配满人，不再参与分配
						if (girlnumber == girllessnumber) {
							classdormitory.add(girld);
							it.remove();
							break;
						} else if (girlnumber < girllessnumber) {
							classdormitory.add(girld);
//							该宿舍退出分配，不再参与
							it.remove();
						} else {
							// 该专业分班实情
							DivideDormitoryInformation ddi = new DivideDormitoryInformation();
							// 设置实情专业
							ddi.setMajor(mac.getMajor());
							// 设置实情班级
							ddi.setClass_id(cas.getClassId());
							// 设置实情性别
							ddi.setSex("女");

//							当前为2人宿舍,出现两人宿舍不满足前判断条件时，必为多出一个未分配，即将此2人宿舍暂时分配给这个班，
//							并将此宿舍登记到专业分宿舍情况集合中
							if (girld.getNumber() == 2) {
								girld.setNumber(girld.getNumber() - 1);
								classdormitory.add(girld);
								it.remove();
								// 设置实情宿舍号
								ddi.setDormitory_id(girld.getId());
								// 设置实情空余床位
								ddi.setManynumber(1);
							} else if (girld.getNumber() == 3) { // 3人宿舍，满足人数分别为2人时才给与宿舍
								// 若空床位多于1，则不再分配床位，与其他宿舍形成混合宿舍
								if (girlnumber - (girllessnumber) > 1) {
									// 设置实情宿舍号
									ddi.setDormitory_id(girld.getId());
									// 设置实情缺少床位数
									ddi.setLessnumber(girld.getNumber() - (girlnumber - (girllessnumber)));
								} else {
									girld.setNumber(girld.getNumber() - (girlnumber - (girllessnumber)));
									classdormitory.add(girld);
									it.remove();
									// 设置实情宿舍号
									ddi.setDormitory_id(girld.getId());
									// 设置实情空余床位
									ddi.setManynumber(girlnumber - (girllessnumber));
								}
							} else { // 4-5人宿舍
										// 若只有2人未分配，则不再分配床位，与其他宿舍形成混合宿舍
								if (girlnumber - (girllessnumber) > 2) {
									// 设置实情宿舍号
									ddi.setDormitory_id(girld.getId());
									// 设置实情缺少床位数
									ddi.setLessnumber(girld.getNumber() - (girlnumber - (girllessnumber)));
								} else {
									girld.setNumber(girld.getNumber() - (girlnumber - (girllessnumber)));
									classdormitory.add(girld);
									it.remove();
									// 设置实情宿舍号
									ddi.setDormitory_id(girld.getId());
									// 设置实情空余床位
									ddi.setManynumber(girlnumber - (girllessnumber));
								}
							}
							girlmajorddis.add(ddi);
							ddi = null;
							break;
						}

					}

					for (Dormitory d1 : classdormitory) {
						DormitoryAllocation da = new DormitoryAllocation();
//					设置班级编号
						da.setClassId(cas.getClassId());
//					设置宿舍号
						da.setDormitoryId(d1.getId());
						da.setNumber(d1.getNumber());
						// 加入集合中，后续统一更新
						das.add(da);
						da = null;
					}

					classdormitory = null;
				} // end----->for
			}
			/**
			 * 对专业分班时出现的情况统计，尽量专业内混合宿舍
			 */
//		若该专业男生不是恰好分配
			if (boymajorddis.size() > 0) {
//			专业分男生宿舍多余床位宿舍
				List<DivideDormitoryInformation> manyboymajorddis = new ArrayList<>();				
//		男生		
				Iterator<DivideDormitoryInformation> boymajorddisit = boymajorddis.iterator();
				while (boymajorddisit.hasNext()) {
					DivideDormitoryInformation boyddi = boymajorddisit.next();
//				如此宿舍有空余床位,则加入多余床位宿舍集合，并从专业分宿舍情况集合中删除，这样剩下的就是缺少床位的情况
					if (boyddi.getManynumber() > 0) {
						manyboymajorddis.add(boyddi);
						boymajorddisit.remove();
					}
				}
//			无多余床位宿舍,将整个缺少情况上报全院---》拟完成先判断是否可以形成一个宿舍
				if (manyboymajorddis.size() == 0) {
					lessMajorDormitor(boyallddis,boymajorddis,boydormitorylist,das);
					//boyallddis.addAll(boymajorddis);
				} else {
//				有多余床位，遍历空位集合

					sumDormitory(boymajorddis, manyboymajorddis, boyallddis, das);

//				如专业内空余床位不够，则将缺少情况上报学校
					if (boymajorddis.size() > 0) {
						lessMajorDormitor(boyallddis,boymajorddis,boydormitorylist,das);
						//boyallddis.addAll(boymajorddis);
					}
					// 专业内空余床位剩余。上报学校
					if (manyboymajorddis.size() > 0) {
						boyallddis.addAll(manyboymajorddis);
					}
				}
				manyboymajorddis = null;
			}

//		若该专业女生不是恰好分配
			if (girlmajorddis.size() > 0) {
//			专业分女生宿舍多余床位宿舍
				List<DivideDormitoryInformation> manygirlmajorddis = new ArrayList<>();

//		女生		
				Iterator<DivideDormitoryInformation> girlmajorddisit = girlmajorddis.iterator();
				while (girlmajorddisit.hasNext()) {
					DivideDormitoryInformation girlddi = girlmajorddisit.next();
//				如此宿舍有空余床位,则加入多余床位宿舍集合，并从专业分宿舍情况集合中删除，这样剩下的就是缺少床位的情况
					if (girlddi.getManynumber() > 0) {
						manygirlmajorddis.add(girlddi);
						girlmajorddisit.remove();
					}
				}
//			无多余床位宿舍,将整个缺少情况上报全院
				if (manygirlmajorddis.size() == 0) {
					lessMajorDormitor(girlallddis,girlmajorddis,girldormitorylist,das);
					//girlallddis.addAll(girlmajorddis);
					
				} else {
					sumDormitory(girlmajorddis, manygirlmajorddis, girlallddis, das);
//					如专业内空余床位不够，则将缺少情况上报学校
					if (girlmajorddis.size() > 0) {
						lessMajorDormitor(girlallddis,girlmajorddis,girldormitorylist,das);
						//girlallddis.addAll(girlmajorddis);
					}
					// 专业内空余床位剩余。上报学校
					if (manygirlmajorddis.size() > 0) {
						girlallddis.addAll(manygirlmajorddis);
					}
				}
				manygirlmajorddis = null;
			}
			girlmajorddis = null;
			boymajorddis = null;
		} // end----->for

		/**
		 * 对学校分班时出现的情况统计，形成混合宿舍
		 */
//	男生

//		若男生不是恰好分配
		if (boyallddis.size() > 0) {
//			专业分男生宿舍多余床位宿舍
			List<DivideDormitoryInformation> manyboyallddis = new ArrayList<>();

//		男生		
			Iterator<DivideDormitoryInformation> boyallddisit = boyallddis.iterator();
			while (boyallddisit.hasNext()) {
				DivideDormitoryInformation boyddi = boyallddisit.next();
//				如此宿舍有空余床位,则加入多余床位宿舍集合，并从专业分宿舍情况集合中删除，这样剩下的就是缺少床位的情况
				if (boyddi.getManynumber() > 0) {
					manyboyallddis.add(boyddi);
					boyallddisit.remove();
				}
			}
//			无多余床位宿舍,将试图调用自定义方法新增宿舍
			if (manyboyallddis.size() == 0) {
				/**
				 * 处理床位不够，boyallddis集合为缺少情况
				 */
				addDormitory(boyallddis, boydormitorylist, das);

			} else {
//				有多余床位，遍历空位集合
				sumDormitory(boyallddis, manyboyallddis, null, das);

				// 如专业内空余床位不够，则将缺少情况上报学校
				if (boyallddis.size() > 0) {

					/**
					 * 处理床位不够
					 */
					addDormitory(boyallddis, boydormitorylist, das);
				}
			}
			manyboyallddis = null;
		}

//	女生	

//		若女生不是恰好分配
		if (girlallddis.size() > 0) {
//			专业分女生宿舍多余床位宿舍
			List<DivideDormitoryInformation> manygirlallddis = new ArrayList<>();

//		女生		
			Iterator<DivideDormitoryInformation> girlallddisit = girlallddis.iterator();
			while (girlallddisit.hasNext()) {
				DivideDormitoryInformation girlddi = girlallddisit.next();
//				如此宿舍有空余床位,则加入多余床位宿舍集合，并从专业分宿舍情况集合中删除，这样剩下的就是缺少床位的情况
				if (girlddi.getManynumber() > 0) {
					manygirlallddis.add(girlddi);
					girlallddisit.remove();
				}
			}
//			无多余床位宿舍,将试图调用自定义方法新增宿舍
			if (manygirlallddis.size() == 0) {
				/**
				 * 处理床位不够，girlallddis集合为缺少情况
				 */
				addDormitory(girlallddis, girldormitorylist, das);
			} else {
//				有多余床位，遍历空位集合
				sumDormitory(girlallddis, manygirlallddis, null, das);

//			如专业内空余床位不够，则将缺少情况上报学校
				if (girlallddis.size() > 0) {
					/**
					 * 处理床位不够
					 */
					addDormitory(girlallddis, girldormitorylist, das);
				}
			}
//			manygirlallddis = null;
		}

		String resultword = "";
		// 存在还缺少床位的女生
		if (girlallddis.size() > 0) {
			for (DivideDormitoryInformation girld : girlallddis) {
				resultword += girld.getMajor() + "专业女生缺少" + girld.getLessnumber() + "个床位:::";
			}
		}
		// 存在还缺少床位的男生
		if (girlallddis.size() > 0) {
			for (DivideDormitoryInformation girld : boyallddis) {
				resultword += girld.getMajor() + "专业男生缺少" + girld.getLessnumber() + "个床位:::";
			}
		}
		if (!resultword.equals("")) {
			resultword = "但" + resultword + "请马上处理！！";
		}
		girlallddis = null;
		boyallddis = null;
//		对分宿舍结果进行更新到数据库
		if (das.size() == 0) {
			return RespBean.error("分宿舍失败");
		} else {
			for (DormitoryAllocation da : das) {
				System.out.println("宿舍：" + da.getDormitoryId() + "班级：" + da.getClassId() + "床位：" + da.getNumber());
			}
		}
		int result = this.dormitoryAllocationService.insertDivideDormitory(das);
		if (result > 0) {
			List<Dormitory> allormitorylist = new ArrayList<Dormitory>();
			// 修改宿舍表
			for (DormitoryAllocation da : das) {

				// 测试
				if (da.getDormitoryId() == 495) {
					int i = 0;
				}

				if (allormitorylist.size() == 0) {
					Dormitory newd = new Dormitory();
					newd.setId(da.getDormitoryId());
					newd.setHasnumber(da.getNumber());
					allormitorylist.add(newd);
				} else {
					boolean isdy = false;
					for (int di = 0; di < allormitorylist.size(); di++) {
						int temp = allormitorylist.get(di).getId();
						if (temp == 495) {
							int j = 0;
						}
						if (temp == da.getDormitoryId()) {// 存在相同宿舍,宿舍人数相加
							allormitorylist.get(di)
									.setHasnumber(allormitorylist.get(di).getHasnumber() + da.getNumber());
							isdy = true;
							break;
						}
					}
					if (!isdy) { // 不存在相同的宿舍
						Dormitory newd = new Dormitory();
						newd.setId(da.getDormitoryId());
						newd.setHasnumber(da.getNumber());
						allormitorylist.add(newd);
					}

				}

			}
			int dsi = this.dormitoryService.updateListDormitory(allormitorylist);
			if (dsi > 0) {
				return RespBean.ok("班级分配宿舍成功！" + resultword);
			} else {
				return RespBean.error("分宿舍失败" + resultword);
			}

		} else {
			return RespBean.error("分宿舍失败" + resultword);
		}

	}

	/**
	 * 根据条件返回宿舍
	 * 
	 * @param ddmc
	 * @return
	 */
	@RequestMapping("/getallDormitory")
	public List<Dormitory> getAllDormitory(DDMC ddmc) {
		return this.dormitoryService.selectAllDormitory(ddmc);
	}

	@Transactional
	@RequestMapping("/insertDormitory")
	public RespBean insertDormitory(Dormitory d) {
		int i = -1;
		List<Dormitory> ds = new ArrayList<>();
		ds.add(d);
		i = this.dormitoryService.insertListDormitory(ds);
		if (i > 0) {
			return RespBean.ok("插入成功！");
		} else {
			return RespBean.error("插入失败！请稍后再试！");
		}
	}

	@Transactional
	@RequestMapping("/updatetDormitory")
	public RespBean updatetDormitory(Dormitory d) {
		int i = -1;
		List<Dormitory> ds = new ArrayList<>();
		ds.add(d);
		i = this.dormitoryService.updateListDormitory(ds);
		if (i > 0) {
			return RespBean.ok("修改成功！");
		} else {
			return RespBean.error("修改失败！请稍后再试！");
		}
	}

	@Transactional
	@RequestMapping("/deleteDormitory")
	public RespBean deleteDormitory(String id) {
		int i = -1;
		String[] tempid = id.split(",");
		List<Dormitory> idlist = new ArrayList<>();
		for (String tid : tempid) {
			Dormitory d = new Dormitory();
			d.setId(Integer.parseInt(tid));
			idlist.add(d);
			d = null;
		}
		i = this.dormitoryService.deleteinsertListDormitory(idlist);
		if (i > 0) {
			return RespBean.ok("删除成功！");
		} else {
			return RespBean.error("删除失败！请稍后再试！");
		}
	}

	@RequestMapping("/getStudentByDormitoryId")
	public List getStudentByDormitoryId(DormitoryRegistration dr) {
		ArrayList list = new ArrayList<>();
		DormitoryAllocation da = new DormitoryAllocation();
		da.setDormitoryId(dr.getDormitoryId());
//		获取班级分配信息
		List<DormitoryAllocation> das = this.dormitoryAllocationService.getDormitoryAllocationByDA(da);
		List<Class> cs = new ArrayList<>();
		for (DormitoryAllocation d : das) {
			Class c = this.ClassService.selectByPrimaryKey(d.getClassId());
			cs.add(c);
		}

//		获取学生分配信息
		List<DormitoryRegistration> drs = this.dormitoryRegistrationService.getDormitoryRegistrationByDR(dr);
		List<StudentStatus> sss = new ArrayList<>();
		for (DormitoryRegistration d : drs) {
			StudentStatus ss = new StudentStatus();
			ss.setStudentId(d.getStudentId());
			sss.add(ss);
			ss = null;
		}
		if (sss.size() > 0) {
//			获取学生详细信息
			sss = this.studentStatusService.getStudentByStudentStatus(sss);
		}
		list.add(cs);
		list.add(sss);
		return list;
	}

	/**
	 * 根据专业返回班级
	 * 
	 * @return
	 */
	@RequestMapping("/getClassByMajorId")
	public List<Class> getClassByMajorId(String majorId) {
		List<Class> cs = this.ClassService.selectClassByMajorID(majorId);
		return cs;
	}

	/**
	 * 根据专业返回班级
	 * 
	 * @return
	 */
	@RequestMapping("/getAllMajor")
	public List<Major> getAllMajor() {
		List<Major> ms = this.majorService.selectAllMajor(new Major());
		return ms;
	}

	/*
	 * 
	 * 获取学院楼栋数据
	 * 
	 */
	@RequestMapping("/getDormitoryDong")
	public List<String> getDormitoryDong(String sex) {
		return this.dormitoryService.getDormitoryDong(sex);
	}

	/*
	 * 
	 * 获取学院楼层数据
	 * 
	 */
	@RequestMapping("/getDormitoryFloor")
	public List<String> getDormitoryFloor(String dong) {
		return this.dormitoryService.getDormitoryFloor(dong);
	}

	/**
	 * 获取所有班级男或女未满足宿舍床位信息
	 * 
	 * @return
	 */
	@RequestMapping("/getIinsufficientClassDormitoryBySex")
	public List<ClassIdAndStudentList> getIinsufficientClassDormitory(String sex) {
		List<ClassIdAndStudentList> list = new ArrayList<>();
//			获取所有班级
		List<Class> ncs = this.ClassService.selectAllClass(new Class());
		for (Class nc : ncs) {
			ClassIdAndStudentList cas = new ClassIdAndStudentList();
			// 获取男女学生
			List<StudentStatus> bsss = this.studentStatusService.selectStudentBySexAndClass(sex, nc.getClassId());

			// 获取该班级所分配到的宿舍床位数，分男女
			DormitoryAllocation da = new DormitoryAllocation();
			da.setClassId(nc.getClassId());
			da.setSex(sex);
			List<DormitoryAllocation> bdas = this.dormitoryAllocationService.getDormitoryAllocationByDA(da);
			int bnumber = 0;
			for (DormitoryAllocation newd : bdas) {
				bnumber += newd.getNumber();
			}

			if (bsss.size() > bnumber) { // 缺少床位
				cas.setLessnumber(bsss.size() - bnumber);
			}

			cas.setC(nc);
			if (cas.getLessnumber() > 0) {

				list.add(cas);
			}

		}

		return list;
	}

	/**
	 * 将该宿舍分配给某班级
	 * 
	 * @param ddis
	 * @param ddi
	 */
	@Transactional
	@RequestMapping("/divideDormitory")
	public RespBean divideDormitory(DormitoryAllocation da) {
		List<DormitoryAllocation> das = new ArrayList<DormitoryAllocation>();
		das.add(da);
		int i = this.dormitoryAllocationService.insertDivideDormitory(das);
		if (i > 0) {
			Dormitory d = this.dormitoryService.selectByPrimaryKey(da.getDormitoryId());
			d.setHasnumber(d.getHasnumber() + da.getNumber());
			List<Dormitory> ds = new ArrayList<Dormitory>();
			ds.add(d);
			int j = this.dormitoryService.updateListDormitory(ds);
			if (j > 0) {
				return RespBean.ok("成功分配宿舍！");
			} else {
				return RespBean.error("分配宿舍失败！请稍后继续！");
			}
		} else {
			return RespBean.error("分配宿舍失败！请稍后继续！");
		}

	}

	// 自定义方法将List中新增元素提到第一位
	public void addFirstList(List<DivideDormitoryInformation> ddis, DivideDormitoryInformation ddi) {
		ddis.add(ddi);
		DivideDormitoryInformation newddi, oldddi = null;
//		取出第一个存起来
		oldddi = ddis.get(0);
		ddis.set(0, ddi);
		for (int i = 1; i < ddis.size(); i++) {
			newddi = ddis.get(i);
			ddis.set(i, oldddi);
			oldddi = newddi;
		}
	}

	// 自定义方法处理混合宿舍，(缺少情况，多余情况，更高一级记录数据集，记录安排)
	public void sumDormitory(List<DivideDormitoryInformation> allddis, List<DivideDormitoryInformation> dormitorylist,
			List<DivideDormitoryInformation> boyallddis, List<DormitoryAllocation> das) {
		if (dormitorylist.size() == 0) {
			if (boyallddis != null) {
				if (dormitorylist.size() > 0) {
					boyallddis.addAll(dormitorylist);// 把空床位上报
				}
			} else {
				return;
			}
		} else {

			Iterator<DivideDormitoryInformation> dd = dormitorylist.iterator();
			loop: while (dd.hasNext()) {
				DivideDormitoryInformation d = dd.next();
				for (int i = 0; i < d.getManynumber();) {
					if (allddis.size() == 0) {
						break loop;
					}

					// 获取第一个缺少床位数据，并删除
					Iterator<DivideDormitoryInformation> newddi = allddis.iterator();
					DivideDormitoryInformation ddi = newddi.next();
					newddi.remove();

					i += ddi.getLessnumber();// 将该情况缺人数取出来并加上i
					DormitoryAllocation da = new DormitoryAllocation();
					da.setDormitoryId(d.getDormitory_id());
					da.setClassId(ddi.getClass_id());
					if (i > d.getManynumber()) {
						da.setNumber(ddi.getLessnumber() - (ddi.getLessnumber() - (i - d.getManynumber())));
					} else {
						da.setNumber(ddi.getLessnumber());
					}
					das.add(da);
					da = null;
					if (i > d.getManynumber()) {
						ddi.setLessnumber(ddi.getLessnumber() - (i - d.getManynumber()));
						addFirstList(allddis, ddi);
					}
				}
//				删除
				dd.remove();
			}
		}

	}

	// 自定义方法处理无空余床位宿舍，先从空余床位宿舍寻找床位，再试图新增宿舍
	public void addDormitory(List<DivideDormitoryInformation> allddis, List<Dormitory> dormitorylist,
			List<DormitoryAllocation> das) {
//		处理不够床位，必须要有床铺

		// 若无宿舍可分配
		if (dormitorylist.size() == 0) {
			return;
		} else {
			Iterator<Dormitory> it = dormitorylist.iterator();
			loop: while (it.hasNext()) {
				Dormitory d = it.next();
				for (int i = 0; i < d.getNumber();) {// 新宿舍，全部可以安排
					if (allddis.size() == 0) {
						break loop;
					}
					DivideDormitoryInformation ddi = allddis.remove(0);
					i += ddi.getLessnumber();// 将该情况缺人数取出来并加上i
					DormitoryAllocation da = new DormitoryAllocation();
					da.setDormitoryId(d.getId());
					da.setClassId(ddi.getClass_id());
					if (i > d.getNumber()) { // 人数多于宿舍床位数是时，将多余人数回退
						da.setNumber(ddi.getLessnumber() - (ddi.getLessnumber() - (i - d.getNumber())));
					} else {
						da.setNumber(ddi.getLessnumber());
					}
					das.add(da);
					da = null;
					if (i > d.getNumber()) {
						ddi.setLessnumber(ddi.getLessnumber() - (i - d.getNumber()));
						addFirstList(allddis, ddi);
					}
				}
				it.remove();
			}
		}

	}
	
	/*
	 * 处理专业内缺少床位宿舍
	 * 
	 * 参数
	 * 院级分配情况集合
	 * 专业缺少宿舍班级集合
	 * 空余宿舍集合
	 * 总宿舍班级分配集合
	 */
	public void lessMajorDormitor(List<DivideDormitoryInformation> allddis,
			List<DivideDormitoryInformation> majorddis,List<Dormitory> dormitorylist,
			List<DormitoryAllocation> das) {
		
			Iterator<Dormitory> it = dormitorylist.iterator();
			loop: while (it.hasNext()) {
				//查看此时缺少的床位数
				int lessnumber=0;
				for(DivideDormitoryInformation ddi:majorddis) {
					lessnumber+=ddi.getLessnumber();
				}
				Dormitory d = it.next();
				//只有满足至少比新宿舍床位数少一个人才可以
				if(d.getNumber()-lessnumber<=1) {
					for (int i = 0; i < d.getNumber();) {// 新宿舍，全部可以安排
						if (majorddis.size() == 0) {
							it.remove();
							break loop;
						}
						DivideDormitoryInformation ddi = majorddis.remove(0);
						i += ddi.getLessnumber();// 将该情况缺人数取出来并加上i
						DormitoryAllocation da = new DormitoryAllocation();
						da.setDormitoryId(d.getId());
						da.setClassId(ddi.getClass_id());
						if (i > d.getNumber()) { // 人数多于宿舍床位数是时，将多余人数回退
							da.setNumber(ddi.getLessnumber() - (ddi.getLessnumber() - (i - d.getNumber())));
						} else {
							da.setNumber(ddi.getLessnumber());
						}
						das.add(da);
						da = null;
						if (i > d.getNumber()) {
							ddi.setLessnumber(ddi.getLessnumber() - (i - d.getNumber()));
							addFirstList(majorddis, ddi);
						}
					}
					it.remove();
				}else {//不满足分配新宿舍
					allddis.addAll(majorddis);
					break loop;
				}
			}
	}
}

///**
//* 处理专业内缺少床位,缺少集合，宿舍集合，记录集合
//*/
//public void addMajorDormitory(List<DivideDormitoryInformation> allddis, List<Dormitory> dormitorylist,
//		List<DormitoryAllocation> das) {
//	Iterator<Dormitory> it = dormitorylist.iterator();
//	loop: while (it.hasNext()) {
//		Dormitory d = it.next();
//		int lessnum=0;
//		Iterator<DivideDormitoryInformation> lessit = allddis.iterator();
//		loops: while (lessit.hasNext()) {
//			DivideDormitoryInformation lessd = lessit.next();
//			lessnum+=lessd.getLessnumber();
//			if(lessnum>=d.getNumber()) {
//				if(lessnum>d.getNumber()) {
//					
//				}
//			}
//		}
//	}
//}

////有宿舍，取出一个进行分配
//Dormitory d2 = dormitorylist.get(0);
////临时变量，存放取出来的缺少人数
//int number = 0;
//while (true) {
//	if (allddis.size() == 0) {
//		break;
//	}
//	DivideDormitoryInformation ddi = allddis.remove(0);
//	number += ddi.getLessnumber();
////	若可以分配
//	if (number <= d2.getNumber()) {
//		DormitoryAllocation da = new DormitoryAllocation();
//		da.setDormitoryId(d2.getId());
//		da.setClassId(ddi.getClass_id());
//		das.add(da);
//		da = null;
//		break;
//	} else { // 不可分配
////		还需要人数
//		int lacknumber = d2.getNumber() - number;
////		不需要人数
//		int notlocknumber = ddi.getLessnumber() - lacknumber;
//
//		ddi.setLessnumber(notlocknumber);
////		将人数减掉可以分配进入的人数重新加入整体列表
//		addFirstList(allddis, ddi);
//
//		DormitoryAllocation da = new DormitoryAllocation();
////		将此宿舍分配给该班级
//		da.setDormitoryId(d2.getId());
//		da.setClassId(ddi.getClass_id());
//		das.add(da);
//		da = null;
//		break;
//	}
//}
