package com.spring.biz.file.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.biz.image.service.ImageService;
import com.spring.biz.image.vo.ImageVO;
import com.spring.biz.pet.service.PetService;
import com.spring.biz.pet.vo.PetVO;
import com.spring.biz.user.service.UserService;
import com.spring.biz.user.vo.UserVO;

@Service
public class FileService {
	@Autowired
	UserService userService;
	
	@Autowired
	PetService petService;
	
	@Autowired
	ImageService imageService;

	public String getDir(UserVO vo, HttpSession session) {
		return session.getServletContext().getRealPath("/upload/profile_user/");
	}

	public String getDir(PetVO vo, HttpSession session) {
		return session.getServletContext().getRealPath("/upload/profile_pet/");
	}

	public String getDir(HttpSession session) {
		return session.getServletContext().getRealPath("/upload/image_main/");
	}

	public String getThumb(HttpSession session) {
		return session.getServletContext().getRealPath("/upload/image_thumb/");
	}

	public String getExt(MultipartFile file) {
		return FilenameUtils.getExtension(file.getOriginalFilename());
	}
	
	public String insertImage(UserVO vo, MultipartHttpServletRequest request) throws Exception {
		String fileName = null;
		
		MultipartFile file = request.getFile("uploadFile");
		HttpSession session = request.getSession();
		
		if(!file.isEmpty()) {
			fileName = vo.getId() + "." + getExt(file);
			file.transferTo(new File(getDir(vo, session) + fileName));
		}
		
		return fileName;
	}

	public String insertImage(PetVO vo, MultipartHttpServletRequest request) throws Exception {
		String fileName = null;
		
		MultipartFile file = request.getFile("uploadFile");
		HttpSession session = request.getSession();
		
		if(!file.isEmpty()) {
			fileName = vo.getMasterId() + "-" + Integer.toString(vo.getPetKey()) + "." + getExt(file);
			file.transferTo(new File(getDir(vo, session) + fileName));
		}
		
		return fileName;
	}

	public String insertImage(ImageVO vo, MultipartHttpServletRequest request) throws Exception {
		String fileName = null;
		
		MultipartFile file = request.getFile("uploadFile");
		HttpSession session = request.getSession();
		
		if(!file.isEmpty()) {
			fileName = vo.getMasterId() + "-"
					+ new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())
					+ "." + getExt(file);
			file.transferTo(new File(getDir(session) + fileName));
			makeThumbnail(getDir(session) + fileName, getThumb(session) + fileName, getExt(file));
		}

		return fileName;
	}

	public String deleteImage(UserVO vo, HttpServletRequest request) {
		String result = null;
		
		HttpSession session = request.getSession();
		UserVO dbVO = userService.selectOne(vo);
		
		File dbfile = new File(getDir(vo, session) + dbVO.getProfileImage());
		result = "유저 프로필 이미지 파일 " + (dbfile.exists() ? dbfile.delete() ? "삭제 성공" : "삭제 실패" : "미존재");
		return result;
	}

	public String deleteImage(PetVO vo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		PetVO dbVO = petService.selectOne(vo);
		File dbfile = new File(getDir(vo, session) + dbVO.getPetImage());
		return "펫 프로필 이미지 파일 " + (dbfile.exists() ? dbfile.delete() ? "삭제 성공" : "삭제 실패" : "미존재");
	}

	public String deleteImage(ImageVO vo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ImageVO dbVO = imageService.selectOne(vo);

		File dbfile1 = new File(getDir(session) + dbVO.getFileName());
		File dbfile2 = new File(getThumb(session) + dbVO.getFileName());

		String msg1 = "이미지 메인 파일 " + (dbfile1.exists() ? dbfile1.delete() ? "삭제 성공" : "삭제 실패" : "미존재");
		String msg2 = "이미지 썸네일 파일 " + (dbfile2.exists() ? dbfile2.delete() ? "삭제 성공" : "삭제 실패" : "미존재");

		return msg1 + msg2;
	}

	private void makeThumbnail(String inputFile, String outputFile, String fileExt) throws Exception {

		BufferedImage srcImg = ImageIO.read(new File(inputFile));

		int dw = 300, dh = 300;
		int ow = srcImg.getWidth();
		int oh = srcImg.getHeight();

		int nw = ow;
		int nh = (ow * dh) / dw;

		if (nh > oh) {
			nh = (oh * dw) / dh;
			nh = oh;
		}

		BufferedImage cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);
		BufferedImage destImg = Scalr.resize(cropImg, dw, dh);

		File thumbFile = new File(outputFile);
		ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
	}
}