package com.pjwards.aide.domain.enums;

public enum Role {
    PARTICIPANT{
        @Override
        public String toString(){
            return "PARTICIPANT";
        }
    }, HOST{
        @Override
        public String toString(){
            return "HOST";
        }
    }, MANAGER{
        @Override
        public String toString(){
            return "MANAGER";
        }
    }, SPEAKER{
        @Override
        public String toString(){
            return "SPEAKER";
        }
    }, ADMIN{
        @Override
        public String toString(){
            return "ADMIN";
        }
    }, USER{
        @Override
        public String toString(){
            return "USER";
        }
    }
}
