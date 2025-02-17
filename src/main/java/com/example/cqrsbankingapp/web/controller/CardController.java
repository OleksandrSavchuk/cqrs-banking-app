package com.example.cqrsbankingapp.web.controller;

import com.example.cqrsbankingapp.domain.model.Card;
import com.example.cqrsbankingapp.service.card.CardService;
import com.example.cqrsbankingapp.web.dto.CardDto;
import com.example.cqrsbankingapp.web.dto.TransactionDto;
import com.example.cqrsbankingapp.web.dto.mapper.CardMapper;
import com.example.cqrsbankingapp.web.dto.mapper.TransactionMapper;
import com.example.cqrsbankingapp.web.security.SecurityUser;
import com.example.cqrsbankingapp.web.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;
    private final SecurityService securityService;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public void create() {
        SecurityUser user = securityService.getUserFromRequest();
        cardService.createByClientId(user.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ssi.canAccessCard(#id)")
    public CardDto getById(@PathVariable UUID id) {
        Card card = cardService.getById(id);
        return cardMapper.toDto(card);
    }

    @GetMapping("/{id}/transactions")
    @PreAuthorize("@ssi.canAccessCard(#id)")
    public List<TransactionDto> getTransactionsByCardId(@PathVariable UUID id) {
        Card card = cardService.getById(id);
        return transactionMapper.toDto(card.getTransactions());
    }

}
