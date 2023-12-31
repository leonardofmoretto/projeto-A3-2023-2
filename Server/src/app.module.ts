import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UsersModule } from './users/users.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from './users/entities/user.entity';
import { GamesModule } from './games/games.module';


@Module({
  imports: [TypeOrmModule.forRoot({
    type: 'mysql',
    host: 'localhost',
    port: 3306,
    username: 'root',
    password: '456654',
    database: 'jogos',
    entities: [User],
    synchronize: true,
  }),
  UsersModule,
  GamesModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
