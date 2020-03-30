package org.jeecg.modules.buss.mediastream.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.buss.mediastream.entity.VideoStream;
import org.jeecg.modules.buss.mediastream.service.IVideoStreamService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: tb_video_stream
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Api(tags="tb_video_stream")
@RestController
@RequestMapping("/mediastream/videoStream")
@Slf4j
public class VideoStreamController extends JeecgController<VideoStream, IVideoStreamService> {
	@Autowired
	private IVideoStreamService videoStreamService;
	
	/**
	 * 分页列表查询
	 *
	 * @param videoStream
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tb_video_stream-分页列表查询")
	@ApiOperation(value="tb_video_stream-分页列表查询", notes="tb_video_stream-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VideoStream videoStream,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<VideoStream> queryWrapper = QueryGenerator.initQueryWrapper(videoStream, req.getParameterMap());
		Page<VideoStream> page = new Page<VideoStream>(pageNo, pageSize);
		IPage<VideoStream> pageList = videoStreamService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param videoStream
	 * @return
	 */
	@AutoLog(value = "tb_video_stream-添加")
	@ApiOperation(value="tb_video_stream-添加", notes="tb_video_stream-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VideoStream videoStream) {
		videoStreamService.save(videoStream);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param videoStream
	 * @return
	 */
	@AutoLog(value = "tb_video_stream-编辑")
	@ApiOperation(value="tb_video_stream-编辑", notes="tb_video_stream-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VideoStream videoStream) {
		videoStreamService.updateById(videoStream);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_video_stream-通过id删除")
	@ApiOperation(value="tb_video_stream-通过id删除", notes="tb_video_stream-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		videoStreamService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_video_stream-批量删除")
	@ApiOperation(value="tb_video_stream-批量删除", notes="tb_video_stream-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.videoStreamService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_video_stream-通过id查询")
	@ApiOperation(value="tb_video_stream-通过id查询", notes="tb_video_stream-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VideoStream videoStream = videoStreamService.getById(id);
		if(videoStream==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(videoStream);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param videoStream
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VideoStream videoStream) {
        return super.exportXls(request, videoStream, VideoStream.class, "tb_video_stream");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VideoStream.class);
    }

}
