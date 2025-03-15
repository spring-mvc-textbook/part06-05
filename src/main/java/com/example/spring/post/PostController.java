package com.example.spring.post;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    // 파일 업로드 경로
    private final String uploadPath = "C:/upload/post";

    // 게시글 등록 (화면, GET)
    @GetMapping("/create")
    public String create() {
        return "post/create";
    }

    // 게시글 등록 (처리, POST)
    @PostMapping("/create")
    public String createPost(PostDto post, RedirectAttributes redirectAttributes) {
        try {
            // 파일 업로드 처리
            MultipartFile uploadFile = post.getUploadFile();
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 저장
                File destFile = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(destFile);

                // 파일 정보 설정
                post.setFileName(fileName);
                post.setOriginalFileName(originalFileName);
            }

            // 게시글 저장
            boolean created = postService.create(post);

            if (created) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
                return "redirect:/posts/";
            }

            redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
            return "redirect:/posts/create";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/posts/create";
        }
    }

    // 게시글 목록 (화면, GET)
    @GetMapping("")
    public String listGet(
        @RequestParam(value = "page", defaultValue = "1") int currentPage, 
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword,
        Model model
    ) {
        int listCountPerPage = 10;  // 한 페이지에서 불러올 게시글 수
        int pageCountPerPage = 5;   // 한 페이지에서 보여질 페이지 수
        Map<String, Object> result = postService.list(currentPage, listCountPerPage, pageCountPerPage, searchType, searchKeyword);
        model.addAttribute("posts", result.get("posts"));
        model.addAttribute("pagination", result.get("pagination"));
        model.addAttribute("searchType", result.get("searchType"));
        model.addAttribute("searchKeyword", result.get("searchKeyword"));
        return "post/list";
    }

    // 게시글 보기 (화면, GET)
    @GetMapping("/{id}")
    public String readGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/read";
    }

    // 게시글 수정 (화면, GET)
    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/update";
    }

    // 게시글 수정 (처리, POST)
    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        post.setId(id);

        try {
            // 기존 게시글 정보 조회
            PostDto originalPost = postService.read(post.getId());

            // 파일 처리
            MultipartFile uploadFile = post.getUploadFile();
            String existingFileName = originalPost.getFileName();

            // 기존 파일 삭제 처리
            if (post.isDeleteFile() || (uploadFile != null && !uploadFile.isEmpty())) {
                if (existingFileName != null) {
                    File fileToDelete = new File(uploadPath + File.separator + existingFileName);
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                    // 파일 정보 초기화
                    post.setFileName(null);
                    post.setOriginalFileName(null);
                }
            } else {
                // 파일을 삭제하지 않고 유지하는 경우
                post.setFileName(existingFileName);
                post.setOriginalFileName(originalPost.getOriginalFileName());
            }

            // 새 파일 업로드 처리
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 저장
                File destFile = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(destFile);

                // 파일 정보 설정
                post.setFileName(fileName);
                post.setOriginalFileName(originalFileName);
            }

            // 게시글 수정
            boolean updated = postService.update(post);
            if (updated) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
                return "redirect:/posts/" + id;
            }

            redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
            return "redirect:/posts/" + id + "/update";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
           return "redirect:/posts/" + id + "/update";
        }
    }

    // 게시글 삭제 (처리, POST)
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        // 기존 게시글 정보
        PostDto originalPost = postService.read(id);

        // 기존 파일 삭제 처리
        if (originalPost.getFileName() != null) {
            File fileToDelete = new File(uploadPath + File.separator + originalPost.getFileName());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }

        post.setId(id);
        boolean deleted = postService.delete(post);

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
            return "redirect:/posts";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
        return("redirect:/posts/" + id);
    }

    // 파일 다운로드
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable("id") int id) {
        try {
            // 게시글 정보
            PostDto post = postService.read(id);
            if (post == null || post.getFileName() == null) {
                return ResponseEntity.notFound().build();
            }

            // 파일 경로 생성
            Path filePath = Paths.get(uploadPath).resolve(post.getFileName());
            Resource resource = new UrlResource(filePath.toUri());

            // 파일이 존재하고 읽을 수 있는지 확인
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 다운로드될 파일명 설정 (원본 파일명 사용)
            String fileName = post.getOriginalFileName();

            // 한글 파일명 처리
            String encodedDownloadName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedDownloadName + "\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
