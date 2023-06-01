package com.johara.email.service;

import com.johara.email.client.ProductServiceClient;
import com.johara.email.client.UserServiceClient;
import com.johara.email.model.OrderMessage;
import com.johara.email.model.ProductDTO;
import com.johara.email.model.UserDTO;
import com.johara.email.model.UserMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailSendingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSendingService.class);
    private UserServiceClient userServiceClient;
    private ProductServiceClient productServiceClient;

    @Autowired
    EmailSendingService(UserServiceClient userServiceClient, ProductServiceClient productServiceClient) {
        this.userServiceClient = userServiceClient;
        this.productServiceClient = productServiceClient;
    }

    public void sendOrderConfirmationEmail(OrderMessage orderMessage) throws IOException {
        LOGGER.info("Order Confirmation #{}", orderMessage.getOrderId());

        UserDTO user = userServiceClient.getUserById(orderMessage.getCustomerId());
        ProductDTO product = productServiceClient.getProductById((Long) orderMessage.getProductId());

        Email from = new Email("adamrbyrne@gmail.com");
        String subject = "Order Confirmation #" + orderMessage.getOrderId();
        Email to = new Email(orderMessage.getCustomerEmail());

        ClassPathResource resource = new ClassPathResource("email-template.html");
        byte[] templateBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        String template = new String(templateBytes, StandardCharsets.UTF_8);
        template = template
                .replace("{{oId}}", orderMessage.getOrderId())
                .replace("{{name}}", user.getName())
                .replace("{{pName}}", product.getName())
                .replace("{{pDescription}}", product.getDescription())
                .replace("{{pQuantity}}", String.valueOf(orderMessage.getQuantity()))
                .replace("{{pPrice}}", String.valueOf(product.getPrice()));

        Content content = new Content("text/html", template);
        LOGGER.info(template, content);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                LOGGER.info("Email sent successfully");
            } else {
                LOGGER.error("Failed to send email. Status code: " +
                        response.getStatusCode());
            }
        } catch (IOException ex) {
            LOGGER.error("Error sending email");
        }
    }

}
