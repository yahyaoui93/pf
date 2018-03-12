﻿import { Component, Input, Optional, OnInit } from '@angular/core';
import { AbstractControl } from '@angular/forms';

import { ValidationMessagesConfiguration, defaultConfig } from './config';
import { MessageProvider } from './message-provider';

@Component({
    selector: 'ng2-mdf-validation-message',
    template: '<span *ngIf="errorMessage !== null" [class]="config.class">{{errorMessage}}</span>'
})
export class ValidationMessageComponent implements OnInit {
    @Input() control: AbstractControl;
    @Input() class: string;

    config: ValidationMessagesConfiguration;
    messageProvider: MessageProvider;

    constructor( @Optional() private customConfig: ValidationMessagesConfiguration) {
        this.config = Object.assign({}, defaultConfig);

        if (customConfig) {
            this.config = Object.assign({}, defaultConfig, customConfig);
        }

        const errorMessages = Object.assign({}, defaultConfig.defaultErrorMessages, this.config.defaultErrorMessages);
        this.messageProvider = new MessageProvider(errorMessages);
    }

    ngOnInit(): void {
        this._mergeWithLocalConfiguration();
    }

    get errorMessage(): string {
        for (let errorPropertyName in this.control.errors) {
            return this.messageProvider.getErrorMessage(errorPropertyName, this.control.errors[errorPropertyName]);
        }

        return null;
    }

    /**
     * Merge instance specific configuration with the default and/or custom one.
     */
    private _mergeWithLocalConfiguration(): void {
        if (this.class) {
            this.config.class = this.class;
        }
    }
}
