package dou.ding.nyat.blog.controller.admin;

import dou.ding.nyat.blog.model.Account;
import dou.ding.nyat.blog.model.Author;
import dou.ding.nyat.blog.model.SocialLink;
import dou.ding.nyat.blog.service.AccountService;
import dou.ding.nyat.blog.service.AuthorService;
import dou.ding.nyat.blog.util.FileStore;
import dou.ding.nyat.blog.util.PasswordUtils;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.datatable.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AuthorController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/author")
    public String account() {
        return "admin/author";
    }

    @PostMapping("/author/list")
    @ResponseBody
    public DataTableResponse<Author> accountList(@RequestBody DataTableRequest dataTableRequest) {
        List<Author> authors = authorService.getTableData(dataTableRequest, "id", "code", "name");
        DataTableResponse<Author> dataTableResponse = new DataTableResponse<>(authors);
        dataTableResponse.setDraw(dataTableRequest.getDraw());
        dataTableResponse.setRecordsFiltered(authorService.countTableDataRecords(dataTableRequest, "id", "code", "name"));
        dataTableResponse.setRecordsTotal(authorService.countAllRecords());
        return dataTableResponse;
    }

    @PostMapping("/account/add")
    @ResponseBody
    public String add(@ModelAttribute Account account) {
        try {

            if (account.getAuthor().getAvatarFile() != null && !account.getAuthor().getAvatarFile().isEmpty()) {
                String ext = Objects.requireNonNull(account.getAuthor().getAvatarFile()
                        .getOriginalFilename()).substring(account.getAuthor().getAvatarFile().getOriginalFilename().lastIndexOf("."));
                String avatarUrl = System.currentTimeMillis() + "-author" + ext;
                Path path = Paths.get(FileStore.IMAGE_UPLOAD_PATH + File.separator + avatarUrl);
                Files.write(path, account.getAuthor().getAvatarFile().getBytes());
                account.getAuthor().setAvatarUrl(avatarUrl);
            }

            account.setPassword(PasswordUtils.encryptPassword(account.getPassword()));
            account.setActived(true);
            accountService.create(account);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @PutMapping("/author/update")
    @ResponseBody
    public String update(@ModelAttribute Author author) {
        try {

            if (author.getAvatarFile() != null && !author.getAvatarFile().isEmpty()) {
                String ext = Objects.requireNonNull(author.getAvatarFile()
                        .getOriginalFilename()).substring(author.getAvatarFile().getOriginalFilename().lastIndexOf("."));
                String avatarUrl = System.currentTimeMillis() + "-author" + ext;
                Path path = Paths.get(FileStore.IMAGE_UPLOAD_PATH + File.separator + avatarUrl);
                Files.write(path, author.getAvatarFile().getBytes());
                author.setAvatarUrl(avatarUrl);
            }

            authorService.update(author);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @GetMapping("/author/multiple-delete/{ids}")
    @ResponseBody
    public String multipleDelete(@PathVariable("ids") List<Integer> ids) {
        try {
            for (Integer id : ids) {
                authorService.delete(id);
            }
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    @DeleteMapping("/author/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        try {
            authorService.delete(id);
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    @DeleteMapping("/author/delete-link")
    @ResponseBody
    public String deleteLink(@RequestParam("linkId") Integer linkId, @RequestParam("authorId") Integer authorId) {
        try {
            authorService.deleteLink(linkId, authorId);
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    @PostMapping("/author/get-account/{id}")
    @ResponseBody
    public Account getAccount(@PathVariable("id") Integer id) {
        Account account = accountService.getByAuthorId(id);
        if (account != null) account.setPassword("");
        return account;
    }

    @PutMapping("/account/update")
    @ResponseBody
    public String updateAccount(@ModelAttribute Account account) {
        try {
            if (account.getPassword() != null && !account.getPassword().isEmpty())
                account.setPassword(PasswordUtils.encryptPassword(account.getPassword()));
            accountService.update(account);
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    @PutMapping("/author/add-link/{id}")
    @ResponseBody
    public String addLink(@PathVariable("id") Integer authorId, @ModelAttribute SocialLink socialLink) {
        try {
            authorService.addLink(authorId, socialLink);
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }
}
